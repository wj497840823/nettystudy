### TCP的粘包/拆包原因及其解决方法是什么

- TCP是基于字节流的，虽然应用层和TCP传输层之间的数据交互是大小不等的数据块，但是TCP把这些数据块仅仅看成一连串无结构的字节流，没有边界。另外从TCP的帧结构也可以看出，在TCP的首部没有表示数据长度的字段。常见的发生原因 
  - 要发送的数据大于TCP发送缓冲区剩余空间大小，将会发生拆包 
  - 待发送数据大于MSS（最大报文长度），TCP在传输前将进行拆包 
  - 要发送的数据小于TCP发送缓冲区的大小，TCP将多次写入缓冲区的数据一次发送出去，将会发生粘包 
  - 接收数据端的应用层没有及时读取接收缓冲区中的数据，将发生粘包 
- 解决问题的关键在于如何给每个数据包添加边界信息，常用的方法如下 
  - 发送端给每个数据包添加包首部，首部中应该至少包含数据包的长度，这样接收端在接收到数据后，通过读取包首部的长度字段，便知道每一个数据包的实际长度了 
  - 发送端将每个数据包封装为固定长度（不够的可以通过补0填充），这样接收端每次从接收缓冲区中读取固定长度的数据就自然而然的把每个数据包拆分开来。
  - 可以在数据包之间设置边界，如添加特殊符号，这样，接收端通过这个边界就可以将不同的数据包拆分开。

### Netty的粘包/拆包是怎么处理的，有哪些实现

- 消息定长，报文大小固定长度，不够空格补全，发送和接收方遵循相同的约定，这样即使粘包了通过接收方编程实现获取定长报文也能区分。
- 包尾添加特殊分隔符，例如每条报文结束都添加回车换行符（例如FTP协议）或者指定特殊字符作为报文分隔符，接收方通过特殊分隔符切分报文区分。
- 将消息分为消息头和消息体，消息头中包含表示信息的总长度（或者消息体长度）的字段 (自定义协议+自定义编解码器)

### IO模型说明

- BIO：同步并阻塞（面向流）
- - 适用于连接数目比较小且固定的架构，对服务器资源要求高，开发简单
- NIO:同步非阻塞（面向块或者说面向缓冲区）
- - 适用于连接数目多且连接比较短（轻操作）的架构，如聊天服务器，服务器之间的通讯
- AIO:异步非阻塞
- - 适用于连接数目多且连接时间比较长（重操作）的架构，如相册服务器

### NIO核心部分及关系

- Channel（通道）
- Buffer（缓冲区）
- Selector（选择器）

![1584943816954](C:\Users\wangjie\AppData\Local\Temp\1584943816954.png)

- 核心部分关系
- - 每个channel对应一个buffer
  - selector对应一个线程，一个线程对应一个channel
  - 该图反应了有三个channel注册到selector
  - 程序切换到哪个channel是由事件决定的，event是一个重要的概念
  - selector会根据不同的事件，在各个通道上切换
  - Buffer就是一个内存块，底层实现中包含一个数组
  - 数据的读取和写入都是通过Buffer，需要调用flip方法进行切换

### Nio网络编程原理分析

- ![1584944504319](C:\Users\wangjie\AppData\Local\Temp\1584944504319.png)
- 当客户端连接时，会通过ServerSocketChannel得到SocketChannel
- Selector进行监控，select方法，返回有事件发生的通道的个数
- 将socketChannel注册到Selector上，register（Selector sel，int ops），一个selector上可以注册多个SocketChannel上
- 注册后返回一个SelectionKey，会和该selecotr关联（集合）
- 进一步得到各个SelectionKey（有事件发生）
- 在通过SelectionKey反向获取SocketChannel，放大channel（）

### 零拷贝（作为了解）



### Reactor模式及分类

- reactor模式优点

- - 响应快，不必为单个同步时间所阻塞，虽然reactor本身依然是同步的
  - 可以最大程度的避免复杂的多线程及同步问题，并且避免了多线程/进程的切换开销
  - 扩展性好，可以方便的通过增加Reactor实例个数来充分利用cpu资源
  - 复用性好，reactor模型本身与具体事件处理逻辑无关，具有很高的复用性

- 单Reactor单线程

- - ![1584945855294](C:\Users\wangjie\AppData\Local\Temp\1584945855294.png)
  - 优缺点
  - - 优点：模型简单，没有多线程，进程通信和竞争的问题，全部都在一个线程中完成
    - 缺点：性能问题和可靠性问题

- 单Reactor多线程

- - ![1584946150101](C:\Users\wangjie\AppData\Local\Temp\1584946150101.png)
  - 优点：可以充分利用多核cpu的处理能力
  - 缺点：多线程数据的共享和访问比较复杂，reactor处理所有事件的监听和响应，在单线程运行，高并发场景容易出现性能瓶颈

- 主从Reactor多线程

- - ![1584949286745](C:\Users\wangjie\AppData\Local\Temp\1584949286745.png)
  - 优点：
    - 父线程与子线程的数据交互简单职责明确，父线程只需要接收新连接，子线程完成后续的业务处理
    - 父线程和子线程的数据交互简单,Reactor主线程只需要把新连接传给子线程，子线程无需返回数据
  - 缺点：编程复杂度较高

  ### netty工作原理图

  ![1584950978737](C:\Users\wangjie\AppData\Local\Temp\1584950978737.png)

  

- netty抽象出两组线程池BossGroup（专门负责接收客户端的连接）和WorkerGroup（专门负责网络的读写）

- BossGroup和WorkerGroup类型都是NioEventLoopGroup

- NioEventLoopGroup相当于一个事件循环组，这个组中含有多个事件循环，每一个事件循环是NioEventLoop

- NioEventLoop表示一个不断循环的执行处理任务的 线程，每个NioEventLoop都有一个selector，用于监听绑定在其上的socket的网络通信

- NioEventLoopGroup可以有多个NioEventLoop

- 每个Boss NioEventLoopGroup循环执行的步骤

- 1. 轮询accept事件
  2. 处理accept事件，与client建立连接，生成NioSocketChannel，并将其注册到某个worker NioEventLoop上的selector
  3. 处理任务队列上的任务，即runAllTasks

- 每个worker NioEventLoop循环执行的步骤

- 1. 轮询read/write事件
  2. 处理i/o事件，即read和write事件，在对应的NioSocketChannel处理
  3. 处理任务队列的任务，即runAllTasks

- 每个worker NioEventLoopGroup处理业务时，会适用pipeline（管道），pipeline中包含了channel，即通过pipeline可以获取对应通道，管道中维护了很多的处理器

### netty的核心模块组件

- BootStrap和ServerBootStrap

- - bootStrap是客户端启动引导类
  - ServerBootStrap是服务器端启动引导类
  - 核心方法...

- Future和ChannelFuture

- - Netty上所有的io操作都是异步的,不能立刻得知消息的返回结果,可以通过Future异步获取执行结束的结果,future可以注册一个监听,当操作成功或失败时会自动触发注册的监听事件
  - 常用方法
  - - Channel channel() 返回当前正在进行IO操作的通道
    - ChannelFuture sync() 等待异步操作执行完毕

- Channel

- - Netty网络通信的组件,可以用于执行网络I/O的操作

  - 通过channel可以当前网络连接通道的状态等信息

  - Channel也可获得网络连接配置的参数

  - ...

  - 常用的Channel类型

  - - NioSocketChannel 异步的客户端TCP Socket连接
    - NioServerSocketChannel  异步的服务端TCP Socket连接
    - NioDatagramChannel  异步的UDP连接

    ..

- selector

- - 基于selector实现I/O多路复用 ...

- ChannelHandler及其实现类

- - ChannelHandler是一个接口,处理I/O事件或者拦截I/O操作,并将其转化到其ChannelPipeline(业务处理链)中的下一个处理程序
  - ChannelHandler常用的子类
  - - ChannelInbound...入栈事件
    - ChannelOutBound...出栈事件
    - ChannelDuplex..  入栈和出栈事件

- Pipeline和ChannelPipeline

  - ChannelPipeline是一个handler集合,它负责处理和拦截inbound和outbound的事件和操作,相当于一个贯穿Netty的链(也可以这样理解:ChannelPipiline是保存ChannelHandler的List,用于处理或拦截Channel的入栈和出栈操作
  - ChannelPipeline实现了一种高级形式的拦截过滤器模式,使用户可以完全控制事件的处理方式,以及Channel中各个的ChannelHandler如何相互交互
  - 在Netty中每个Channel都有且仅有一个ChannelPipeline与之对应.
  - 常用方法
  - - ChannelPipeline addFirst(ChannelHandler .. handlers) 把handler添加到链中的第一个位置
    - ChannelPipeline addLast....   把handler....最后一个位置

- ChannelhandlerContext

- ChannelOption

- EventLoopGroup和NioEventLoopGroup

- Unpooled类

