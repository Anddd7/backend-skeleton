package coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal class SimpleCoroutineTest {
  @Test
  fun `thread in java`() {
    val thread1 = Thread {
      Thread.sleep(1000)
      println("hello, [${Thread.currentThread().name}]")
    }
    val thread2 = Thread {
      println("hello, [${Thread.currentThread().name}]")
    }

    thread1.start()
    thread2.start()

    println("after start")

    thread1.join()
    thread2.join()
  }

  @Test
  fun `thread in executor`() {
    val executor = Executors.newFixedThreadPool(2)
    executor.execute {
      Thread.sleep(1000)
      println("hello, [${Thread.currentThread().name}]")
    }
    executor.execute {
      println("hello, [${Thread.currentThread().name}]")
    }

    println("after execute")

    when (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
    }
  }

  /**
   * runBlocking
   * 通常适用于单元测试的场景，而业务开发中不会用到这种方法，因为它是线程阻塞的。
   */
  @Test
  fun `runBlocking in kotlin`() {
    runBlocking {
      delay(1000)
      println("hello, [${Thread.currentThread().name}]")
    }
    runBlocking {
      println("hello, [${Thread.currentThread().name}]")
    }
    println("after run blocking")
  }

  /**
   * GlobalScope
   * 使用 runBlocking 的区别在于不会阻塞线程, 但GlobalScope单例对象的生命周期会和整个程序一致, 无法取消
   */
  @Test
  fun `global scope in kotlin`() = runBlocking {
    val launch1 = GlobalScope.launch {
      delay(1000)
      println("hello, [${Thread.currentThread().name}]")
    }
    val launch2 = GlobalScope.launch {
      println("hello, [${Thread.currentThread().name}]")
    }
    println("after GlobalScope.launch")

    // GlobalScope启动的协程必须显示的join到当前线程, 否则当前协程执行完毕后会直接退出
    launch1.join()
    launch2.join()
  }

  /**
   * CoroutineScope
   * 我们可以通过 scope 参数去管理和控制协程的生命周期
   */
  @Test
  fun `new scope in kotlin`() = runBlocking {
    launch {
      delay(1000)
      println("hello, [${Thread.currentThread().name}]")
    }
    launch {
      println("hello, [${Thread.currentThread().name}]")
    }
    println("after GlobalScope.launch")

    // 使用内部的scope启动的协程会自动join, 等待所有协程完成后当前协程才算完成
  }

  /**
   * CoroutineContext & CoroutineDispatcher
   * launch & async 等函数可接受CoroutineContext, 里面包含了CoroutineDispatcher, 即决定协程应该由哪些线程来执行
   */
  @Test
  fun `dispatchers in kotlin`() = runBlocking {
    // 使用父协程的上下文, 也就是当前的 runBlocking 协程所在的线程
    launch {
      delay(1000)
      println("hello, [${Thread.currentThread().name}]")
    }
    // 将会在IO线程中执行 (默认也是Default, 不过可以调整数量), 针对磁盘和网络 IO 进行了优化
    launch(Dispatchers.IO) {
      delay(1000)
      println("hello, [${Thread.currentThread().name}]")
    }
    // 会被派发到 DefaultDispatcher
    launch(Dispatchers.Default) {
      println("hello, [${Thread.currentThread().name}]")
    }
    // 非受限 -- 将会在主线程中执行
    launch(Dispatchers.Unconfined) {
      println("hello, [${Thread.currentThread().name}]")
    }
    // 将会在独自的新线程内执行
    launch(newSingleThreadContext("MyOwnThread")) {
      println("hello, [${Thread.currentThread().name}]")
    }
    // for UI 相关的程序的
//    launch(Dispatchers.Main) {
//      println("hello, [${Thread.currentThread().name}]")
//    }
    println("after GlobalScope.launch")
  }

  /**
   * launch 启动一个协程, 立即执行
   * async 启动一个协程, 可以返回一个延迟可读的结果包装
   *
   * suspend 挂起执行到suspend函数的协程
   * |  挂起-> 跳出当前协程代码, 被系统回收 或 执行其他后台任务
   * |  e.g 如果这个线程它是 Android 的主线程，那它接下来就会继续回去工作：也就是一秒钟 60 次的界面刷新任务
   *
   * launch 中出现 suspend 时会挂起当前协程, 因此 getCode 和 getValue 是顺序执行
   * async 中出现 suspend 时不会直接挂起协程, 会将返回结果包装在 Deferred 里面, 在 await 的时候再挂起
   */
  @Test
  fun `launch and async, suspend and merge`() = runBlocking {
    var desc = ""
    launch {
      println("desc is :$desc")
    }
    launch {
      delay(600)
      println("desc is :$desc")
    }
    launch {
      //      val code = getCode()
      //      val value =  getValue()
      val code = async { getCode() }
      val value = async { getValue() }
      val name = getName(code.await())

      desc = "${code.await()}, ${value.await()}, $name"

      println("after concat all suspend things")
    }

    println("after launch all things")
  }

  /**
   * 父协程(线程)的代码在执行到suspend时被掐断, 而suspend函数会被分配给指定的线程(dispatchers)执行
   * 函数执行完成后, 会自动切回到父协程(线程) !!! (调度 resume)
   * |  由协程管理调度操作, 所以挂起函数必须在协程里面使用
   *
   * suspend函数本身并不会挂起协程, 只是作为语法(编译警告)提示
   * 实际作用的是withContext
   */
  private suspend fun getCode() = withContext(Dispatchers.IO) {
    delay(500)
    "CODE"
  }

  private suspend fun getValue() = withContext(Dispatchers.IO) {
    delay(500)
    "Value"
  }

  private suspend fun getName(code: String) = withContext(Dispatchers.IO) {
    "Name of $code"
  }
}
