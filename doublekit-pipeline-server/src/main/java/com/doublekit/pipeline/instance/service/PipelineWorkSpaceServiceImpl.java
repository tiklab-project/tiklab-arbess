package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.execute.model.CodeGit.FileTree;
import com.doublekit.pipeline.execute.model.CodeGit.GitCommit;
import com.doublekit.pipeline.execute.model.PipelineCode;
import com.doublekit.pipeline.execute.service.PipelineCodeService;
import com.doublekit.pipeline.instance.service.execAchieveService.CommonAchieveService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import org.apache.commons.lang3.time.DateUtils;
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
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Exporter
public class PipelineWorkSpaceServiceImpl implements  PipelineWorkSpaceService {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    CommonAchieveService commonAchieveService;

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineCodeService pipelineCodeService;

    @Autowired
    PipelineOpenService pipelineOpenService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineWorkSpaceServiceImpl.class);

    //获取文件树
    @Override
    public List<FileTree> fileTree(String pipelineId,String userId){
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        pipelineOpenService.findOpen(userId,pipeline);
        if (pipeline == null)return null;

        //设置拉取地址
        String path = commonAchieveService.getFileAddress()+pipeline.getPipelineName();
        List<FileTree> trees = new ArrayList<>();
        File file = new File(path);
        //判断文件是否存在
        if (file.exists()){
            List<FileTree> list = commonAchieveService.fileTree(file, trees);
            list.sort(Comparator.comparing(FileTree::getTreeType,Comparator.reverseOrder()));
            return list;
        }
        return null;
    }

    //
    @Override
    public  List<String> readFile(String path){
        return commonAchieveService.readFile(path);
    }

    @Override
    public List<List<GitCommit>> getSubmitMassage(String pipelineId){
        PipelineConfigure pipelineConfigure = pipelineConfigureService.findOneConfigure(pipelineId, 10);
        if (pipelineConfigure != null){
            return switch (pipelineConfigure.getTaskType()) {
                case 1, 2, 3, 4 -> git(pipelineId);
                case 5 -> svn(pipelineConfigure);
                default -> null;
            };
        }
        return null;
    }

    public List<List<GitCommit>> git(String pipelineId) {
        List<GitCommit> list = new ArrayList<>();
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        RevWalk walk = null;
        String dir = commonAchieveService.getFileAddress() + pipeline.getPipelineName();
        try (Repository repo = new FileRepository( dir+ "/.git"); Git git = new Git(repo)) {
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
                List<DiffEntry> changedFileList = changedFileList(verCommit, repo);
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
            repo.close();
            git.close();
            if (walk != null) {
                walk.close();
            }
            return returnValue(list);
        } catch (IOException | GitAPIException e) {
            logger.info("流水线git文件地址找不到，或者没有提交信息");
            return null;
        }
    }

    //git提交信息
    public List<DiffEntry> changedFileList(RevCommit revCommit, Repository repo) throws IOException, GitAPIException {
        List<DiffEntry> returnDiffs = null;
        RevCommit overcommitment = prevHash(revCommit, repo);
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
        return returnDiffs;
    }

    //遍历新旧树
    public  RevCommit prevHash(RevCommit commit, Repository repo) throws IOException {
        RevWalk walk = new RevWalk(repo);
        walk.markStart(commit);
        int count = 0;
        for(RevCommit rev :walk){
            if (count == 1) {
                return rev;
            }
            walk.dispose();
            count++;
        }
        return null;
    }

    //获取svn
    public SVNLogEntry[] svnMassage(Proof proof, PipelineCode pipelineCode) throws SVNException {

        //ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(
        //        new File(System.getProperty("java.io.tmpdir")+"/auth"), proof.getProofUsername(), proof.getProofPassword().toCharArray());
        //DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        //options.setDiffCommand("-x -w");

        BasicAuthenticationManager auth;
        if (proof.getProofType().equals("SSH")){
            auth = BasicAuthenticationManager
                    .newInstance(proof.getProofUsername(), new File(proof.getProofPassword()),null,22);
        }else {
            auth = BasicAuthenticationManager.newInstance(proof.getProofUsername(), proof.getProofPassword().toCharArray());
        }

        SVNRepository repos = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(pipelineCode.getCodeAddress()));
        repos.setAuthenticationManager(auth);

        long startRevision = repos.getDatedRevision(DateUtils.addDays(new Date(), -500));
        long endRevision = repos.getDatedRevision(new Date());
        @SuppressWarnings("unchecked")
        Collection<SVNLogEntry> logEntries = repos.log(new String[]{""}, null,
                startRevision, endRevision, true, true);
        SVNLogEntry[] svnLogEntries = logEntries.toArray(new SVNLogEntry[0]);
        SVNLogEntry[] svnLogEntries1;
        if(svnLogEntries.length==0){
            svnLogEntries1 = Arrays.copyOf(svnLogEntries, svnLogEntries.length);
        }else{
            svnLogEntries1 = Arrays.copyOf(svnLogEntries, svnLogEntries.length-1);
        }
        return svnLogEntries1;
    }

    //svn提交信息
    public List<List<GitCommit>> svn(PipelineConfigure pipelineConfigure)  {
        PipelineCode pipelineCode = pipelineCodeService.findOneCode(pipelineConfigure.getTaskId());
        Pipeline pipeline = pipelineConfigure.getPipeline();
        if (pipelineCode.getProof() == null){
            return null;
        }
        try {
            SVNLogEntry[] svnMassage = svnMassage(pipelineCode.getProof(), pipelineCode);
            if (svnMassage == null){
                return null;
            }
            List<GitCommit> list = new ArrayList<>();
            for (SVNLogEntry entry : svnMassage) {
                List<String> strings = new ArrayList<>();
                if (entry.getDate() == null){
                    continue;
                }
                GitCommit commit = new GitCommit();
                commit.setCommitId( ""+entry.getRevision());
                commit.setCommitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entry.getDate()));
                commit.setCommitMassage(entry.getMessage());
                commit.setCommitName(entry.getAuthor());
                commit.setDayTime(new SimpleDateFormat("yyyy-MM-dd").format(entry.getDate()));
                for (Map.Entry<String, SVNLogEntryPath> pathEntry : entry.getChangedPaths().entrySet()) {
                    //文件地址
                    strings.add( commonAchieveService.getFileAddress()+pipeline.getPipelineName()+pathEntry.getKey());
                }
                commit.setCommitFile(strings);
                list.add(commit);
            }
            list.sort(Comparator.comparing(GitCommit::getDayTime,Comparator.reverseOrder()));
            return returnValue(list);
        } catch (SVNException e) {
            return null;
        }
    }

    //封装返回值
    public List<List<GitCommit>> returnValue( List<GitCommit> list){
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
    }



}
