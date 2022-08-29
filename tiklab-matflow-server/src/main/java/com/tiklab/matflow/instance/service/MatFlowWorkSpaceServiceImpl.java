package com.tiklab.matflow.instance.service;



import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.service.MatFlowCommonService;
import com.tiklab.matflow.definition.service.MatFlowConfigureService;
import com.tiklab.matflow.definition.service.MatFlowService;
import com.tiklab.matflow.execute.model.CodeGit.FileTree;
import com.tiklab.matflow.execute.model.CodeGit.GitCommit;
import com.tiklab.matflow.execute.service.MatFlowCodeService;
import com.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

import java.util.*;

@Service
@Exporter
public class MatFlowWorkSpaceServiceImpl implements MatFlowWorkSpaceService {

    @Autowired
    MatFlowService matFlowService;

    @Autowired
    MatFlowConfigureService matFlowConfigureService;

    @Autowired
    MatFlowCodeService matFlowCodeService;

    @Autowired
    MatFlowOpenService matFlowOpenService;

    @Autowired
    MatFlowCommonService matFlowCommonService;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowWorkSpaceServiceImpl.class);

    //获取文件树
    @Override
    public List<FileTree> fileTree(String matFlowId, String userId){
        MatFlow matFlow = matFlowService.findMatFlow(matFlowId);
        if (matFlow == null)return null;
        matFlowOpenService.findOpen(userId, matFlow);
        //设置拉取地址
        String path = matFlowCommonService.getFileAddress()+ matFlow.getMatflowName();
        List<FileTree> trees = new ArrayList<>();
        File file = new File(path);
        //判断文件是否存在
        if (file.exists()){
            List<FileTree> list = matFlowCommonService.fileTree(file, trees);
            list.sort(Comparator.comparing(FileTree::getTreeType,Comparator.reverseOrder()));
            return list;
        }
        return null;
    }

    //读取文件信息
    @Override
    public  List<String> readFile(String path){
        return matFlowCommonService.readFile(path);
    }

    @Override
    public List<List<GitCommit>> getSubmitMassage(String matFlowId){
        MatFlowConfigure matFlowConfigure = matFlowConfigureService.findOneConfigure(matFlowId, 10);
        if (matFlowConfigure != null){
            return switch (matFlowConfigure.getTaskType()) {
                //case 1, 2, 3, 4 -> git(matFlowId);
                //case 5 -> svn(matFlowConfigure);
                default -> null;
            };
        }
        return null;
    }

    //public List<List<GitCommit>> git(String matFlowId) {
        //List<GitCommit> list = new ArrayList<>();
        //MatFlow matFlow = matFlowService.findMatFlow(matFlowId);
        //RevWalk walk = null;
        //String dir = matFlowCommonService.getFileAddress() + matFlow.getMatflowName();
        //try (Repository repo = new FileRepository( dir+ "/.git"); Git git = new Git(repo)) {
        //    Iterable<RevCommit> commits = git.log().all().call();
        //
        //    for (RevCommit commit : commits) {
        //        walk = new RevWalk(repo);
        //        RevCommit verCommit = walk.parseCommit(repo.resolve(commit.getName()));
        //
        //        //初始化信息
        //        GitCommit cit = new GitCommit();
        //        cit.setCommitId(commit.getName());
        //        cit.setCommitName(commit.getAuthorIdent().getName());
        //        cit.setCommitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(commit.getCommitTime() * 1000L)));
        //        cit.setCommitMassage(commit.getFullMessage());
        //        cit.setTime(commit.getCommitTime());
        //        cit.setDayTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date(cit.getTime() * 1000L)));
        //
        //        List<String> arrayList = new ArrayList<>();
        //        List<DiffEntry> changedFileList = changedFileList(verCommit, repo);
        //        if (changedFileList != null) {
        //            for (DiffEntry entry : changedFileList) {
        //                arrayList.add(entry.getNewPath());
        //            }
        //        }
        //        if (changedFileList != null) {
        //            changedFileList.clear();
        //        }
        //        cit.setCommitFile(arrayList);
        //        list.add(cit);
        //    }
        //    //关闭
        //    repo.close();
        //    git.close();
        //    if (walk != null) {
        //        walk.close();
        //    }
        //    return returnValue(list);
        //} catch (IOException | GitAPIException e) {
        //    return null;
        //}
    //}

    //git提交信息
    //private List<DiffEntry> changedFileList(RevCommit revCommit, Repository repo) throws IOException, GitAPIException {
    //    List<DiffEntry> returnDiffs = null;
    //    RevCommit overcommitment = prevHash(revCommit, repo);
    //    if (overcommitment == null){return null;}
    //
    //    //获取新旧树id
    //    ObjectId head = revCommit.getTree().getId();
    //    ObjectId oldHead = overcommitment.getTree().getId();
    //
    //    //获取新旧树信息
    //    ObjectReader reader = repo.newObjectReader();
    //    CanonicalTreeParser oldTreeItem = new CanonicalTreeParser();
    //    oldTreeItem.reset(reader, oldHead);
    //    CanonicalTreeParser newTreeItem = new CanonicalTreeParser();
    //    newTreeItem.reset(reader, head);
    //
    //    //对比新旧树
    //    Git git = new Git(repo);
    //    List<DiffEntry> diffs = git.diff()
    //            .setNewTree(newTreeItem)
    //            .setOldTree(oldTreeItem)
    //            .call();
    //    for (DiffEntry ignored : diffs) {
    //        returnDiffs = diffs;
    //    }
    //    git.close();
    //    return returnDiffs;
    //}

    //遍历新旧树
  //  public  RevCommit prevHash(RevCommit commit, Repository repo) throws IOException {
  //      RevWalk walk = new RevWalk(repo);
  //      walk.markStart(commit);
  //      int count = 0;
  //      for(RevCommit rev :walk){
  //          if (count == 1) {
  //              return rev;
  //          }
  //          walk.dispose();
  //          count++;
  //      }
  //      return null;
  //  }

    //获取svn
    //public SVNLogEntry[] svnMassage(Proof proof, MatFlowCode matFlowCode) throws SVNException {
    //
    //    BasicAuthenticationManager auth;
    //    if (proof.getProofType().equals("SSH")){
    //        auth = BasicAuthenticationManager
    //                .newInstance(proof.getProofUsername(), new File(proof.getProofPassword()),null,22);
    //    }else {
    //        auth = BasicAuthenticationManager.newInstance(proof.getProofUsername(), proof.getProofPassword().toCharArray());
    //    }
    //
    //    SVNRepository repos = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(matFlowCode.getCodeAddress()));
    //    repos.setAuthenticationManager(auth);
    //
    //    long startRevision = repos.getDatedRevision(DateUtils.addDays(new Date(), -500));
    //    long endRevision = repos.getDatedRevision(new Date());
    //    @SuppressWarnings("unchecked")
    //    Collection<SVNLogEntry> logEntries = repos.log(new String[]{""}, null,
    //            startRevision, endRevision, true, true);
    //    SVNLogEntry[] svnLogEntries = logEntries.toArray(new SVNLogEntry[0]);
    //    SVNLogEntry[] svnLogEntries1;
    //    if(svnLogEntries.length==0){
    //        svnLogEntries1 = Arrays.copyOf(svnLogEntries, svnLogEntries.length);
    //    }else{
    //        svnLogEntries1 = Arrays.copyOf(svnLogEntries, svnLogEntries.length-1);
    //    }
    //    return svnLogEntries1;
    //}

    //svn提交信息
    //public List<List<GitCommit>> svn(MatFlowConfigure matFlowConfigure)  {
    //    MatFlowCode matFlowCode = matFlowCodeService.findOneCode(matFlowConfigure.getTaskId());
    //    MatFlow matFlow = matFlowConfigure.getMatFlow();
    //    if (matFlowCode.getProof() == null){
    //        return null;
    //    }
    //    try {
    //        SVNLogEntry[] svnMassage = svnMassage(matFlowCode.getProof(), matFlowCode);
    //        if (svnMassage == null){
    //            return null;
    //        }
    //        List<GitCommit> list = new ArrayList<>();
    //        for (SVNLogEntry entry : svnMassage) {
    //            List<String> strings = new ArrayList<>();
    //            if (entry.getDate() == null){
    //                continue;
    //            }
    //            GitCommit commit = new GitCommit();
    //            commit.setCommitId( ""+entry.getRevision());
    //            commit.setCommitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entry.getDate()));
    //            commit.setCommitMassage(entry.getMessage());
    //            commit.setCommitName(entry.getAuthor());
    //            commit.setDayTime(new SimpleDateFormat("yyyy-MM-dd").format(entry.getDate()));
    //            for (Map.Entry<String, SVNLogEntryPath> pathEntry : entry.getChangedPaths().entrySet()) {
    //                //文件地址
    //                strings.add(matFlowCommonService.getFileAddress()+ matFlow.getMatflowName()+pathEntry.getKey());
    //            }
    //            commit.setCommitFile(strings);
    //            list.add(commit);
    //        }
    //        list.sort(Comparator.comparing(GitCommit::getDayTime,Comparator.reverseOrder()));
    //        return returnValue(list);
    //    } catch (SVNException e) {
    //        return null;
    //    }
    //}

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
