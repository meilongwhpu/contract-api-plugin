## 简介
基于Spring Boot构建智能合约API项目。对于NULS智能合约开发者，启动nuls钱包要占用大量本地资源，启动该API即可进行智能合约的调用。
## API接口
- 创建智能合约，URI:/contract/create
- 获取智能合约构造函数，URI:/contract/constructor
- 测试创建智能合约，URI:/contract/precreate
- 估算创建智能合约的GAS消耗，URI:/contract/imputedgas/create
- 调用智能合约，URI:/contract/call
- 估算调用智能合约的GAS消耗，URI:/contract/imputedgas/call
- 获取智能合约信息，URI:/contract/info/wallet/{address}
- 获取智能合约余额，URI:/contract/balance/{address}
- 获取智能合约交易详情，URI:/contract/tx/{hash}
- 获取智能合约的交易列表，URI:/contract/tx/list/{contractAddress}
- 收藏智能合约地址，URI:/修改备注名称/contract/collection
- 取消收藏智能合约地址，URI:/contract/collection/cancel
- 获取钱包账户的合约地址列表（账户创建的合约以及钱包收藏的合约），URI:/contract/wallet/list/{address}
- 导出合约编译代码的jar包，URI:/contract/export/{address}
- 打包智能合约，URI:/contract/package
- 添加服务器端节点信息，URI:/contract/addServiceNode
- 删除服务器端节点信息，URI:/contract/removeServiceNode
- 添加账户地址，URI:/contract/addAccount/{address}
- 删除账户地址，URI:/contract/delAccount/{address}
 
## 快速开始
在打包生成的jar目录下面，只需jar -jar contract-api-plugin-1.0.jar
 
## 开发建议
启动jar之后，访问http://localhost:8080/swagger-ui.html可以查看API接口的详细信息
 
