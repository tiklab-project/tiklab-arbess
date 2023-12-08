UPDATE "pcs_op_log_template" SET "title" = '流水线运行', "content" = '<div style="display: flex;justify-content: flex-start; align-items:center">
    <div style="width: 25px; height: 25px; line-height: 25px; border-radius: 32px; text-align: center; color: #fff;">
        ${title}
    </div>
    <div style="flex:1;margin: 0 15px">
        <div>
            ${userName} 运行流水线
        </div>
        <div style="display:flex; align-items:center;height: 40px">
            <div style="width: 20px; height: 20px; margin-right: 10px;border-radius: 5px">
                <img src="${img}" alt="" style="width: 100%; height:100%"/>
            </div>
            <div>
                ${pipelineName}-->${message}
            </div>
        </div>
    </div>
    <div style="font-size: 13px">${createTime}</div>
</div>', "link" = '/index/pipeline/${pipelineId}/structure', "bgroup" = 'matflow', "abstract_content" = '流水线运行' WHERE "id" = 'LOG_TEM_RUN';
UPDATE "pcs_op_log_template" SET "title" = '流水线创建', "content" = '<div style="display: flex;justify-content: flex-start; align-items:center">
    <div style="width: 25px; height: 25px; line-height: 25px; border-radius: 32px; text-align: center; color: #fff;">
        ${title}
    </div>
    <div style="flex:1;margin: 0 15px">
        <div>
            ${userName} 创建了流水线
        </div>
        <div style="display:flex; align-items:center;height: 40px">
            <div style="width: 20px; height: 20px; margin-right: 10px;border-radius: 5px">
                <img src="${img}" alt="" style="width: 100%; height:100%"/>
            </div>
            <div>
                ${pipelineName}
            </div>
        </div>
    </div>
    <div style="font-size: 13px">${createTime}</div>
</div>', "link" = '/index/pipeline/${pipelineId}/survey', "bgroup" = 'matflow', "abstract_content" = '流水线创建' WHERE "id" = 'G_TEM_CREATE';
UPDATE "pcs_op_log_template" SET "title" = '流水线删除', "content" = '<div style="display: flex;justify-content: flex-start; align-items:center">
    <div style="width: 25px; height: 25px; line-height: 25px; border-radius: 32px; text-align: center; color: #fff;">
        ${title}
    </div>
    <div style="flex:1;margin: 0 15px">
        <div>
            ${userName} 删除了流水线
        </div>
        <div style="display:flex; align-items:center;height: 40px">
            <div style="width: 20px; height: 20px; margin-right: 10px;border-radius: 5px">
                <img src="${img}" alt="" style="width: 100%; height:100%"/>
            </div>
            <div>
                ${pipelineName}
            </div>
        </div>
    </div>
    <div style="font-size: 13px">${createTime}</div>
</div>', "link" = '/index/pipeline/${pipelineId}/survey', "bgroup" = 'matflow', "abstract_content" = '流水线删除' WHERE "id" = 'G_TEM_DELETE';
UPDATE "pcs_op_log_template" SET "title" = '流水线更新', "content" = '<div style="display: flex;justify-content: flex-start; align-items:center">
    <div style="width: 25px; height: 25px; line-height: 25px; border-radius: 32px; text-align: center; color: #fff;">
        ${title}
    </div>
    <div style="flex:1;margin: 0 15px">
        <div>
            ${userName} 更新流水线
        </div>
        <div style="display:flex; align-items:center;height: 40px">
            <div style="width: 20px; height: 20px; margin-right: 10px;border-radius: 5px">
                <img src="${img}" alt="" style="width: 100%; height:100%"/>
            </div>
            <div>
                ${pipelineName}
            </div>
        </div>
    </div>
    <div style="font-size: 13px">${createTime}</div>
</div>', "link" = '/index/pipeline/${pipelineId}/survey', "bgroup" = 'matflow', "abstract_content" = '流水线更新' WHERE "id" = 'G_TEM_UPDATE';