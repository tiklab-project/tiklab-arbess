package io.tiklab.arbess.support.util.util;

import io.tiklab.rpc.client.RpcClient;
import io.tiklab.rpc.client.config.RpcClientConfig;
import io.tiklab.rpc.client.router.lookup.FixedLookup;

public class PipelineRpc {

    public static PipelineRpc instance (){
        return new PipelineRpc();
    };

    public <T> T findServiceRpc(Class<T>  tClass,String serviceAddress){

        RpcClientConfig rpcClientConfig = RpcClientConfig.instance();
                // .setProtocol(RpcConstants.PROTOCOL_DRPC);
        RpcClient rpcClient = new RpcClient(rpcClientConfig);
        return rpcClient.getBean(tClass,new FixedLookup(serviceAddress));
    }


}
