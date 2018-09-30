#项目分类功能  

###以下代码不含任何业务 任何代码可以使用  


lib-alert   
- 包含最基础的dialog，popWindow，toast工具
    
lib-base
- 基类BaseActivity,BaseFragment,BaseQuickAdapter引用第三方
- BaseQuickAdapter  
  使用指南：https://www.jianshu.com/p/b343fcff51b0  
  源码地址：https://github.com/CymChad/BaseRecyclerViewAdapterHelper


lib-utils
- StringUtils，6.0以上权限申请等工具类
    
lib-retrofit
- rxJava使用时注意内存泄漏问题，
    rxJava学习：
     - 1.https://www.jianshu.com/p/464fa025229e
     - 2.https://juejin.im/post/5b8f536c5188255c352d3528
     - 3.自己多看  
    项目已经引用lifecycle管理rxJava生命周期 
    LinkObserver结合BaseActivity和BaseFragment已经作了生命周期处理，
   如果使用的不是，需要自己使用.compose(mLifecycleProvider.<BaseResponse>bindUntilEvent(Lifecycle.Event.ON_STOP))处理
- retrofit，rxJava初步封装，与业务无关不含文件上传下载功能
- 文件上传下载引用ProgressManager，直接下载demo看如何使用  
  不要仿照demo中在application里面初始化，在需要使用之前初始化即可  
  git项目：https://github.com/JessYanCoding/ProgressManager 
  
  
lib-router  
-封装ARouter一些基本使用
- ARouter学习https://github.com/alibaba/ARouter/blob/master/README_CN.md
 
 tips
    
    代码已上传至maven管理，主要配置gradle_maven_push.gradle文件然后引用
    上传库文件
    gradlew uploadArchives
    更新库文件 速度较慢
    gradlew build --refresh-dependencies 不采用
    
    changing true
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor 1, 'seconds'
    }
