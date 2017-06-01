#MVN learn book 
##静态语言编译
code--compile-->.bin （c,c++,etc)  
code--compile-->.class--JVM-->.bin (java)  

* 只有.bin这个二进制文件才是机器可以识别的.  
* 静态语言分为中间转换型语言（java,.net)和直接编译型语言(c,c++)，java通过JVM来对编译出来的二进制.class文件根据运行平台不同(windows/linux)进行翻译，以实现跨平台。

##测试
* **黑盒测试**：由专业测试人员测试，他们并不关心代码实现或者代码结构，单纯地对软件功能进行测试。
* **白盒测试**：由程序开发人员进行测试，他们了解底层代码结构，有针对性地对软件进测试。白盒测试又分为两种：  
  * 单元测试（常用Junit）：在java中指对一个类的测试，在C中指对一个函数的测试。
  * 集中测试：对多个类所组成的功能模块进行测试。

##MVN五大特性
* 依赖注入
* 生命周期
* 插件配置
* 坐标，仓库
* 聚合与继承

##MVN生命周期
 * 几个主要生命周期：
   * process-resource
   * compile
   * process-test-resource
   * test-compile
   * test
   * package 
   * install
   * deploy
 * 每一个阶段都可以绑定多个插件目标，这些阶段本身是不做事情的，事情都交给插件去做。
 * MVN一共有23个阶段，几乎每个主阶段都有before、after两个阶段（可以看做子阶段）来提供预处理和处理后的插件接口，这是MVN扩展性强大的地方。
 * MVN一共有3套生命周期:
   1. clean:
     * pre-clean
     * clean(default: maven-clean-plugin:clean)
     * post-clean
   2. default: 就是上面几个主要周期
   3. site:
     * pre-site
     * site(default:maven-site-plugin:site)
     * post-site
     * site-deploy(default:maven-site-plugin:deploy)  
 
 主要阶段都有默认插件，用户也可以根绝自己需求进行配置。
 
   			 