# robot
后台管理系统服务端
## H2连接字符串参数
DB_CLOSE_DELAY=-1：要求最后一个正在连接的连接断开后，不要关闭数据库
MODE=MySQL：兼容模式，H2兼容多种数据库，该值可以为：DB2、Derby、HSQLDB、MSSQLServer、MySQL、Oracle、PostgreSQL
AUTO_RECONNECT=TRUE：连接丢失后自动重新连接
AUTO_SERVER=TRUE：启动自动混合模式，允许开启多个连接，该参数不支持在内存中运行模式
TRACE_LEVEL_SYSTEM_OUT、TRACE_LEVEL_FILE：输出跟踪日志到控制台或文件， 取值0为OFF，1为ERROR（默认值），2为INFO，3为DEBUG
SET TRACE_MAX_FILE_SIZE mb：设置跟踪日志文件的大小，默认为16M
## Java线程池七个参数详解
### corePoolSize 线程池核心线程大小

线程池中会维护一个最小的线程数量，即使这些线程处理空闲状态，他们也不会 被销毁，除非设置了allowCoreThreadTimeOut。这里的最小线程数量即是corePoolSize。

### maximumPoolSize 线程池最大线程数量

一个任务被提交到线程池后，首先会缓存到工作队列（后面会介绍）中，如果工作队列满了，则会创建一个新线程，然后从工作队列中的取出一个任务交由新线程来处理，而将刚提交的任务放入工作队列。线程池不会无限制的去创建新线程，它会有一个最大线程数量的限制，这个数量即由maximunPoolSize来指定。

### keepAliveTime 空闲线程存活时间

一个线程如果处于空闲状态，并且当前的线程数量大于corePoolSize，那么在指定时间后，这个空闲线程会被销毁，这里的指定时间由keepAliveTime来设定

### unit 空间线程存活时间单位

keepAliveTime的计量单位

### workQueue 工作队列

新任务被提交后，会先进入到此工作队列中，任务调度时再从队列中取出任务。jdk中提供了四种工作队列：

#### ArrayBlockingQueue

基于数组的有界阻塞队列，按FIFO排序。新任务进来后，会放到该队列的队尾，有界的数组可以防止资源耗尽问题。当线程池中线程数量达到corePoolSize后，再有新任务进来，则会将任务放入该队列的队尾，等待被调度。如果队列已经是满的，则创建一个新线程，如果线程数量已经达到maxPoolSize，则会执行拒绝策略。

#### LinkedBlockingQuene

基于链表的无界阻塞队列（其实最大容量为Interger.MAX），按照FIFO排序。由于该队列的近似无界性，当线程池中线程数量达到corePoolSize后，再有新任务进来，会一直存入该队列，而不会去创建新线程直到maxPoolSize，因此使用该工作队列时，参数maxPoolSize其实是不起作用的。

#### SynchronousQuene

一个不缓存任务的阻塞队列，生产者放入一个任务必须等到消费者取出这个任务。也就是说新任务进来时，不会缓存，而是直接被调度执行该任务，如果没有可用线程，则创建新线程，如果线程数量达到maxPoolSize，则执行拒绝策略。

#### PriorityBlockingQueue

具有优先级的无界阻塞队列，优先级通过参数Comparator实现。

### threadFactory 线程工厂

创建一个新线程时使用的工厂，可以用来设定线程名、是否为daemon线程等等

### handler 拒绝策略

当工作队列中的任务已到达最大限制，并且线程池中的线程数量也达到最大限制，这时如果有新任务提交进来，该如何处理呢。这里的拒绝策略，就是解决这个问题的，jdk中提供了4中拒绝策略：

#### CallerRunsPolicy

该策略下，在调用者线程中直接执行被拒绝任务的run方法，除非线程池已经shutdown，则直接抛弃任务。

#### AbortPolicy

该策略下，直接丢弃任务，并抛出RejectedExecutionException异常。

#### DiscardPolicy

该策略下，直接丢弃任务，什么都不做。

#### DiscardOldestPolicy

该策略下，抛弃进入队列最早的那个任务，然后尝试把这次拒绝的任务放入队列
## 动态数据源切换

### AbstractRoutingDataSource

Spring boot提供了AbstractRoutingDataSource 根据用户定义的规则选择当前的数据源，这样我们可以在执行查询之前，设置使用的数据源。实现可动态路由的数据源，在每次数据库查询操作前执行。它的抽象方法 determineCurrentLookupKey() 决定使用哪个数据源。
AbstractRoutingDataSource的getConnection() 方法根据查找 lookup key 键对不同目标数据源的调用，通常是通过(但不一定)某些线程绑定的事物上下文来实现。
AbstractRoutingDataSource的多数据源动态切换的核心逻辑是：在程序运行时，把数据源数据源通过 AbstractRoutingDataSource 动态织入到程序中，灵活的进行数据源切换。
基于AbstractRoutingDataSource的多数据源动态切换，可以实现读写分离，这么做缺点也很明显，无法动态的增加数据源。

#### 实现逻辑

定义DynamicDataSource类继承抽象类AbstractRoutingDataSource，并实现了determineCurrentLookupKey()方法。
把配置的多个数据源会放在AbstractRoutingDataSource的 targetDataSources和defaultTargetDataSource中，然后通过afterPropertiesSet()方法将数据源分别进行复制到resolvedDataSources和resolvedDefaultDataSource中。
调用AbstractRoutingDataSource的getConnection()的方法的时候，先调用determineTargetDataSource()方法返回DataSource在进行getConnection()。

### 具体实现

在配置文件 application.properties 中添加多个(我这里是两个)数据源的配置信息
在configuration中将多个数据库信息声明为Bean
创建自定义动态数据源切换对象继承AbstractRoutingDataSource
调用AbstractRoutingDataSource的setDefaultTargetDataSource方法传递默认写数据源
调用AbstractRoutingDataSource的setTargetDataSources方法传递所有数据源，类型为Map，key值为自定义序号，value为数据源
重写determineCurrentLookupKey()方法该方法返回targetDataSource(Map)中的key，即决定当前使用哪个数据源

### 线程局部变量配置

创建ThreadLocal线程局部变量，泛型为读写数据库枚举类

### mybatis拦截器配置

在拦截到select语句时，将线程局部变量中的数据库类型置为读，在拦截后清除ThreadLocal中的值

### AOP切面配置

将动态数据源放入SqlSessionFactory和DataSourceTransactionManager对象中