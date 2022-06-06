package com.doublekit.pipeline.execute.service.codeGit;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.execute.model.CodeGit.GitCommit;
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
public class GitCommitServiceImpl implements GitCommitService {

    @Autowired
    PipelineService pipelineService;

    private static final Logger logger = LoggerFactory.getLogger(GitCommitServiceImpl.class);


    public List<List<GitCommit>> getSubmitMassage(String pipelineId) {
        if (pipelineId == null){
            return null;
        }
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        List<GitCommit> list = new ArrayList<>();

        RevWalk walk = null;

        try (Repository repo = new FileRepository("D:\\clone\\" + pipeline.getPipelineName() + "\\.git"); Git git = new Git(repo)) {
            Iterable<RevCommit> commits = git.log().all().call();

            for (RevCommit commit : commits) {
                walk = new RevWalk(repo);
                RevCommit verCommit = walk.parseCommit(repo.resolve(commit.getName()));

                //初始化信息
                GitCommit cit = new GitCommit();
                cit.setCommitId(commit.getName());
                cit.setCommitName(commit.getAuthorIdent().getName());
                cit.setCommitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(commit.getCommitTime() * 1000L)));
                cit.setCommitMassage(commit.getFullMessage());
                cit.setTime(commit.getCommitTime());
                cit.setDayTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date(cit.getTime() * 1000L)));

                List<String> arrayList = new ArrayList<>();
                List<DiffEntry> changedFileList = getChangedFileList(verCommit, repo);
                if (changedFileList != null) {
                    for (DiffEntry entry : changedFileList) {
                        arrayList.add(entry.getNewPath());
                    }
                }
                if (changedFileList != null) {
                    changedFileList.clear();
                }
                cit.setCommitFile(arrayList);
                list.add(cit);
            }
            //关闭
            git.close();
            repo.close();
            if (walk != null) {
                walk.close();
            }

            //封装返回数据
            List<List<GitCommit>> ArrayList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                List<GitCommit> gitCommitArrayList = new ArrayList<>();
                String dayTime = list.get(i).getDayTime();
                for (GitCommit gitCommit : list) {
                    if (dayTime.equals(gitCommit.getDayTime())) {
                        gitCommitArrayList.add(gitCommit);
                        i++;
                    }
                }
                ArrayList.add(gitCommitArrayList);
            }
            return ArrayList;
        } catch (IOException | GitAPIException e) {
            logger.info("流水线git文件地址找不到，或者没有提交信息");
            return null;
        }
    }

    public List<DiffEntry> getChangedFileList(RevCommit revCommit, Repository repo) throws IOException, GitAPIException {
        List<DiffEntry> returnDiffs = null;
        RevCommit overcommitment = getPrevHash(revCommit, repo);
        if (overcommitment == null){return null;}

        //获取新旧树id
        ObjectId head = revCommit.getTree().getId();
        ObjectId oldHead = overcommitment.getTree().getId();

        //获取新旧树信息
        ObjectReader reader = repo.newObjectReader();
        CanonicalTreeParser oldTreeItem = new CanonicalTreeParser();
        oldTreeItem.reset(reader, oldHead);
        CanonicalTreeParser newTreeItem = new CanonicalTreeParser();
        newTreeItem.reset(reader, head);

        //对比新旧树
        Git git = new Git(repo);
        List<DiffEntry> diffs = git.diff()
                .setNewTree(newTreeItem)
                .setOldTree(oldTreeItem)
                .call();
        for (DiffEntry ignored : diffs) {
            returnDiffs = diffs;
        }

        git.close();
        repo.close();
        return returnDiffs;
    }

    //遍历新旧树
    public  RevCommit getPrevHash(RevCommit commit, Repository repo) throws IOException {
        RevWalk walk = new RevWalk(repo);
        walk.markStart(commit);
        int count = 0;
        for(RevCommit rev :walk){
            if (count == 1) {
                walk.close();
                repo.close();
                return rev;
            }
            walk.dispose();
            count++;
        }
        return null;
    }

}
