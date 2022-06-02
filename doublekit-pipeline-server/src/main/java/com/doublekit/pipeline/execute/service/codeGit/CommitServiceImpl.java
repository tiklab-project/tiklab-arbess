package com.doublekit.pipeline.execute.service.codeGit;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.execute.model.CodeGit.Commit;
import com.doublekit.rpc.annotation.Exporter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Exporter
public class CommitServiceImpl implements CommitService{

    @Autowired
    PipelineService pipelineService;

    private static final Logger logger = LoggerFactory.getLogger(CommitServiceImpl.class);


    public List<List<Commit>> getSubmitMassage(String pipelineId) {
        if (pipelineId == null){
            return null;
        }
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        List<Commit> list = new ArrayList<>();
        Repository repo ;
        try {
            repo = new FileRepository("D:\\clone\\"+pipeline.getPipelineName()+"/.git");
            Git git = new Git(repo);
            Iterable<RevCommit> commits = git.log().all().call();
            for (RevCommit commit : commits) {
                Commit cit = new Commit();
                RevWalk walk = new RevWalk(repo);
                RevCommit verCommit = walk.parseCommit(repo.resolve(commit.getName()));
                cit.setCommitId(commit.getName());
                cit.setCommitName(commit.getAuthorIdent().getName());
                cit.setCommitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(commit.getCommitTime() * 1000L)));
                cit.setCommitMassage(commit.getFullMessage());
                cit.setTime(commit.getCommitTime());
                cit.setDayTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date(cit.getTime() * 1000L)));
                List<String> arrayList = new ArrayList<>();

                List<DiffEntry> changedFileList = getChangedFileList(verCommit, repo);
                if (changedFileList!= null){
                    for (DiffEntry entry : changedFileList) {
                        arrayList.add(entry.getNewPath());
                    }
                }
                cit.setCommitFile(arrayList);
                list.add(cit);
            }


            List<List<Commit>> ArrayList = new ArrayList<>();
            for (int i = 0;i<list.size();i++){
                List<Commit> commitArrayList = new ArrayList<>();
                String dayTime = list.get(i).getDayTime();
                for (Commit commit : list) {
                    if (dayTime.equals(commit.getDayTime())) {
                        commitArrayList.add(commit);
                        i++;
                    }
                }
                ArrayList.add(commitArrayList);
            }
            return ArrayList;
        } catch (IOException | GitAPIException e) {
            logger.info("流水线git文件地址找不到，或者没有提交信息");
            return null;
        }
    }

    public List<DiffEntry> getChangedFileList(RevCommit revCommit, Repository repo) throws IOException, GitAPIException {
        List<DiffEntry> returnDiffs = null;
        RevCommit previsoucommit = getPrevHash(revCommit, repo);
        if (previsoucommit == null){return null;}
        ObjectId head = revCommit.getTree().getId();
        ObjectId oldHead = previsoucommit.getTree().getId();

        ObjectReader reader = repo.newObjectReader();
        CanonicalTreeParser oldTreeItem = new CanonicalTreeParser();
        oldTreeItem.reset(reader, oldHead);
        CanonicalTreeParser newTreeItem = new CanonicalTreeParser();
        newTreeItem.reset(reader, head);

        Git git = new Git(repo);
        List<DiffEntry> diffs = git.diff()
                .setNewTree(newTreeItem)
                .setOldTree(oldTreeItem)
                .call();
        for (DiffEntry ignored : diffs) {
            returnDiffs = diffs;
        }
        return returnDiffs;
    }

    public  RevCommit getPrevHash(RevCommit commit, Repository repo) throws IOException {
        RevWalk walk = new RevWalk(repo);
        walk.markStart(commit);
        int count = 0;
        for(RevCommit rev :walk){
            if (count == 1) {
                return rev;
            }
            count++;
            walk.dispose();
        }
        return null;
    }

}
