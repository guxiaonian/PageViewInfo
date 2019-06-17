<div align="center">
  
## PageViewInfo

**获取`Android`页面所有控件的点击事件**

[![GitHub issues](https://img.shields.io/github/issues/guxiaonian/PageViewInfo.svg)](https://github.com/guxiaonian/PageViewInfo/issues)
[![GitHub forks](https://img.shields.io/github/forks/guxiaonian/PageViewInfo.svg)](https://github.com/guxiaonian/PageViewInfo/network)
[![GitHub stars](https://img.shields.io/github/stars/guxiaonian/PageViewInfo.svg)](https://github.com/guxiaonian/PageViewInfo/stargazers)
[![GitHub license](https://img.shields.io/github/license/guxiaonian/PageViewInfo.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Travis CI](https://travis-ci.org/guxiaonian/PageViewInfo.svg?branch=master)](https://travis-ci.org/guxiaonian/PageViewInfo)

</div>
<br>

> 本产品不提供远程依赖,仅提供思路与实现方式

Table of Contents
=================

   * [FrameLayout方式](#FrameLayout方式)
   * [反射方式](#反射方式)
   * [AOP方式](#AOP方式)
   * [疑问解答](#疑问解答)
      * [Aspectj框架的集成方式](#Aspectj框架的集成方式)
      * [三种方法的区别](#三种方法的区别)
      * [获取更多View的点击方法](#获取更多View的点击方法)

分三种方法实现

### FrameLayout方式

###### 代码实现

```java
 PageViewLifecycleHelper.with(this).addPageViewListener(PageViewType.frameLayout,basePageViewListener)
```

###### 参数说明

参数|参数类型|参数说明
---|----|---
PageViewType.frameLayout|int|具体实现方式
basePageViewListener|BasePageViewListener|点击回调

###### BasePageViewListener回调
以下三种方式的回调与之保持一致,所以仅说明一次。

> onClick(view)
普通View的点击事件

参数|参数类型|参数说明
---|----|---
view|View|当前点击的控件布局

> onItemClick(parent,view,position,id)
AdapterView的点击事件

参数|参数类型|参数说明
---|----|---
parent|AdapterView|当前的AdapterView
view|View|当前的AdapterView的item的view的布局
position|int|当前item在AdapterView中适配器里的位置
id|long|当前item在AdapterView中的行数

> onItemSelected(parent,view,position,id)
AdapterView的选择事件

参数|参数类型|参数说明
---|----|---
parent|AdapterView|当前的AdapterView
view|View|当前的AdapterView的item的view的布局
position|int|当前item在AdapterView中适配器里的位置
id|long|当前item在AdapterView中的行数

> onNothingSelected(parent,view,position,id)
 AdapterView的未选择事件

 参数|参数类型|参数说明
 ---|----|---
 parent|AdapterView|当前的AdapterView

> onItemClick(RecyclerView recyclerView, View view, int position)
 RecyclerView的点击事件

 参数|参数类型|参数说明
 ---|----|---
 recyclerView|RecyclerView|当前的RecyclerView
 view|View|当前的RecyclerView的item的view的布局
 position|int|当前item在RecyclerView中适配器里的位置

###### 原理说明

1. 在Application中通过registerActivityLifecycleCallbacks来注册Activity的生命周期。
2. 在onActivityStarted对当前页面的View进行遍历并保存至List中。
3. 在每一个Activity的最外层实现FrameLayout。
4. 通过onInterceptTouchEvent的触摸事件来遍历View来实现。
5. 对AdapterView与RecyclerView则通过重写点击事件来实现。
6. 在onActivityPaused对当前页面的View进行清空。

### 反射方式

###### 代码实现

```java
 PageViewLifecycleHelper.with(this).addPageViewListener(PageViewType.hookView,basePageViewListener)
```

###### 参数说明

参数|参数类型|参数说明
---|----|---
PageViewType.frameLayout|int|具体实现方式
basePageViewListener|BasePageViewListener|点击回调

###### 原理说明

1. 在Application中通过registerActivityLifecycleCallbacks来注册Activity的生命周期。
2. 在onActivityStarted对当前页面的View进行遍历并保存至List中。
3. 反射在View的mOnClickListener中设置自定义的点击事件。
4. 对AdapterView与RecyclerView则通过重写点击事件来实现。
5. 在onActivityPaused对当前页面的View进行清空。

### AOP方式

###### 代码实现

```java
 PageViewAspectjHelper.with().setBasePageViewListener(basePageViewListener)
```

###### 参数说明

参数|参数类型|参数说明
---|----|---
basePageViewListener|BasePageViewListener|点击回调

###### 原理说明

1. 通过Aspectj框架来对全局的View进行切入。
2. 使用@Aspect表示切入的类。
3. 使用@After在切入的方法执行异步执行自定义的相关逻辑。

### 疑问解答

#### Aspectj框架的集成方式

现阶段有开源的框架,可以简单的在Android中使用，如[gradle_plugin_android_aspectjx](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx)。
如果想自己集成,则步骤如下:

* 在[aspectj](https://mvnrepository.com/artifact/org.aspectj)上找到最新的版本。
* 在项目的根build文件内加入相关依赖。如: `classpath 'org.aspectj:aspectjtools:1.9.4'`
* 在使用Aspectj的module中的build文件内加入相关代码与依赖。如

```java
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main

android.libraryVariants.all {
    variant ->  LibraryPlugin plugin = project.plugins.getPlugin(LibraryPlugin)
    JavaCompile javaCompile = variant.javaCompile
    javaCompile.doLast {
        String[] args = ["-showWeaveInfo",
                         "-1.8",
                         "-inpath", javaCompile.destinationDir.toString(),
                         "-aspectpath", javaCompile.classpath.asPath,
                         "-d", javaCompile.destinationDir.toString(),
                         "-classpath", javaCompile.classpath.asPath,
                         "-bootclasspath", plugin.project.android.bootClasspath.join(
                File.pathSeparator)]

        MessageHandler handler = new MessageHandler(true)
        new Main().run(args, handler)

        def log = project.logger
        for (IMessage message : handler.getMessages(null, true)) {
            switch (message.getKind()) {
                case IMessage.ABORT:
                case IMessage.ERROR:
                case IMessage.FAIL:
                    log.error message.message, message.thrown
                    break
                case IMessage.WARNING:
                case IMessage.INFO:
                    log.info message.message, message.thrown
                    break
                case IMessage.DEBUG:
                    log.debug message.message, message.thrown
                    break
            }
        }
    }
}

dependencies {
    api 'org.aspectj:aspectjrt:1.9.4'
}
```

* 在app中的build文件中加入相关代码，如:

```java
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main

final def log = project.logger
final def variants = project.android.applicationVariants

variants.all { variant ->
    if (!variant.buildType.isDebuggable()) {
        log.debug("Skipping non-debuggable build type '${variant.buildType.name}'.")
        return
    }

    JavaCompile javaCompile = variant.javaCompile
    javaCompile.doLast {
        String[] args = ["-showWeaveInfo",
                         "-1.8",
                         "-inpath", javaCompile.destinationDir.toString(),
                         "-aspectpath", javaCompile.classpath.asPath,
                         "-d", javaCompile.destinationDir.toString(),
                         "-classpath", javaCompile.classpath.asPath,
                         "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
        log.debug "ajc args: " + Arrays.toString(args)

        MessageHandler handler = new MessageHandler(true)
        new Main().run(args, handler)
        for (IMessage message : handler.getMessages(null, true)) {
            switch (message.getKind()) {
                case IMessage.ABORT:
                case IMessage.ERROR:
                case IMessage.FAIL:
                    log.error message.message, message.thrown
                    break
                case IMessage.WARNING:
                    log.warn message.message, message.thrown
                    break
                case IMessage.INFO:
                    log.info message.message, message.thrown
                    break
                case IMessage.DEBUG:
                    log.debug message.message, message.thrown
                    break
            }
        }
    }
}
```
* 相关配置完成之后,即可根据相关方法来实现自己的逻辑。

#### 三种方法的区别

* AOP的方式侵入的更全面一点，动态的静态的布局都可以使用。但是只针对设置了相关方法的View才可以。比如View未设置OnClick事件则无法监听。
* FrameLayout的方式只能获取到静态的布局，但是所有控件的点击都可以拿到，无论是否设置了相关方法。
* 反射的方式速度相对比较慢，但也是只能拿到静态布局。

#### 获取更多View的点击方法

现阶段只获取了普通View,AdapterView,RecycleView这几个，当然同样的思路可以放在更多的View上，如：ExpandableListView等等。