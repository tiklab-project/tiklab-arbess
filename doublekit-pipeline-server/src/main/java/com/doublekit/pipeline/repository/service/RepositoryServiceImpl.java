package com.doublekit.pipeline.repository.service;

import com.doublekit.beans.BeanMapper;
import com.doublekit.common.page.Pagination;
import com.doublekit.common.page.PaginationBuilder;
import com.doublekit.dss.client.DssClient;
import com.doublekit.eam.common.Ticket;
import com.doublekit.eam.common.TicketContext;
import com.doublekit.eam.common.TicketHolder;
import com.doublekit.join.JoinTemplate;
import com.doublekit.privilege.role.service.DmRoleService;
import com.doublekit.rpc.annotation.Exporter;
import com.doublekit.user.user.model.DmUser;
import com.doublekit.user.user.model.User;
import com.doublekit.user.user.service.DmUserService;
import com.doublekit.pipeline.category.service.CategoryService;
import com.doublekit.pipeline.document.service.DocumentService;
import com.doublekit.pipeline.repository.dao.RepositoryDao;
import com.doublekit.pipeline.repository.entity.RepositoryEntity;
import com.doublekit.pipeline.repository.model.Repository;
import com.doublekit.pipeline.repository.model.RepositoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RepositoryServiceImpl
*/
@Service
@Exporter
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    RepositoryDao repositoryDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    CategoryService categoryService;

    @Autowired
    DocumentService documentService;

    @Autowired
    DmUserService dmUserService;

    @Autowired
    DmRoleService dmRoleService;

    @Autowired
    DssClient dssClient;

    @Override
    public String createRepository(@NotNull @Valid Repository repository) {
        RepositoryEntity repositoryEntity = BeanMapper.map(repository, RepositoryEntity.class);

        String id = repositoryDao.createRepository(repositoryEntity);
        String masterId = repository.getMaster().getId();
        //初始化项目成员
        DmUser dmUser = new DmUser();
        dmUser.setDomainId(id);
       // dmUser.setUser(new User().setId(findCreatUser()));
        dmUser.setUser(new User().setId(masterId));
        dmUserService.createDmUser(dmUser);

        //初始化项目权限
        dmRoleService.initDmRoles(id,findCreatUser());

        //构建索引
        Repository entity = findRepository(id);
        dssClient.save(entity);
        return id;
    }

    @Override
    public void updateRepository(@NotNull @Valid Repository repository) {
        RepositoryEntity repositoryEntity = BeanMapper.map(repository, RepositoryEntity.class);

        repositoryDao.updateRepository(repositoryEntity);
    }

    @Override
    public void deleteRepository(@NotNull String id) {
        //删除相关联的目录和内容
//        DeleteCondition deleteCondition = DeleteBuilders.createDelete(DocumentEntity.class)
//                .eq("repositoryId", id)
//                .get();
        documentService.deleteDocument(id);

//        deleteCondition = DeleteBuilders.createDelete(CategoryEntity.class)
//                .eq("repositoryId", id)
//                .get();
        categoryService.deleteCategory(id);

        repositoryDao.deleteRepository(id);
    }

    @Override
    public Repository findOne(String id) {
        RepositoryEntity repositoryEntity = repositoryDao.findRepository(id);

        Repository repository = BeanMapper.map(repositoryEntity, Repository.class);
        return repository;
    }

    @Override
    public List<Repository> findList(List<String> idList) {
        List<RepositoryEntity> repositoryEntityList =  repositoryDao.findRepositoryList(idList);

        List<Repository> repositoryList =  BeanMapper.mapList(repositoryEntityList,Repository.class);
        return repositoryList;
    }

    @Override
    public Repository findRepository(@NotNull String id) {
        Repository repository = findOne(id);

        joinTemplate.joinQuery(repository);
        return repository;
    }

    @Override
    public List<Repository> findAllRepository() {
        List<RepositoryEntity> repositoryEntityList =  repositoryDao.findAllRepository();

        List<Repository> repositoryList =  BeanMapper.mapList(repositoryEntityList,Repository.class);

        joinTemplate.joinQuery(repositoryList);
        return repositoryList;
    }

    @Override
    public List<Repository> findRepositoryList(RepositoryQuery repositoryQuery) {
        List<RepositoryEntity> repositoryEntityList = repositoryDao.findRepositoryList(repositoryQuery);

        List<Repository> repositoryList = BeanMapper.mapList(repositoryEntityList,Repository.class);

        joinTemplate.joinQuery(repositoryList);

        return repositoryList;
    }

    @Override
    public Pagination<Repository> findRepositoryPage(RepositoryQuery repositoryQuery) {

        Pagination<RepositoryEntity>  pagination = repositoryDao.findRepositoryPage(repositoryQuery);

        List<Repository> repositoryList = BeanMapper.mapList(pagination.getDataList(),Repository.class);

        joinTemplate.joinQuery(repositoryList);

        return PaginationBuilder.build(pagination,repositoryList);
    }

    /**
     * 查询用户（创建人）id
     * @param
     */
    public String findCreatUser(){
        String ticketId = TicketHolder.get();
        Ticket ticket = TicketContext.get(ticketId);
        return ticket.getUserId();
    }

}