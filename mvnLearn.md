# MVN learn book   
## 静态语言编译  

code--compile-->.bin （c,c++,etc)  
code--compile-->.class--JVM-->.bin (java)  

* 只有.bin这个二进制文件才是机器可以识别的.  
* 静态语言分为中间转换型语言（java,.net)和直接编译型语言(c,c++)，java通过JVM来对编译出来的二进制.class文件根据运行平台不同(windows/linux)进行翻译，以实现跨平台。

## 测试
* **黑盒测试**：由专业测试人员测试，他们并不关心代码实现或者代码结构，单纯地对软件功能进行测试。
* **白盒测试**：由程序开发人员进行测试，他们了解底层代码结构，有针对性地对软件进测试。白盒测试又分为两种：  
  * 单元测试（常用Junit）：在java中指对一个类的测试，在C中指对一个函数的测试。
  * 集中测试：对多个类所组成的功能模块进行测试。

## MVN五大特性
* 依赖注入
* 生命周期
* 插件配置
* 坐标，仓库
* 聚合与继承

## MVN生命周期
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
 
##  MVN插件配置
* 几个标签：
  * `<groupId><artifactId><version>`三个标签来定义插件坐标
  * `<execution>`为插件配置一个任务，其下
  	 * `<id>`标签定义任务名称
  	 * `<phase>`标签指定任务绑定的生命周期
  	 * `<goals>`标签指定任务使用插件的目标
  	 * `<configuration>`表示特定任务的配置
  * `<configuration>`标签如果在`<execution>`标签外面则表示插件式全局配置。

实例：

```
<plugins>  
	<plugin>
  		<groupId>...</groupId>
  		<artifactId>...</artifactId>
  		<version>...</version>
  		<executions>
  			<excution>
  				<id>ant-validate</id>
  				<phase>validate</phase>
  				<goals>
  					<goal>run</goal>
  				</goals>
  				<configuration>
  					<echo>I'm bound to validate</echo>
  				</configuration>
  			</excution>
  		</executions>
  	</plugin>
</plugins>
```

## MVN聚合

**场景：**例如在开发过程中分别实现了注册用户两个功能模块（account-email和account-persist），这个时候会希望能够一次构建两个模块，而不是对两个模块分别用mvn命令进行构建。

因此MVN提供了聚合这个功能，通过创建一个额外的模块（如：account-aggregator)来包含这两个模块，这个‘aa’模块实际上是一个空模块，只有一个pom文件进行配置，没有别的功能，pom配置比较特殊，实例如下：

```
<project ...>
	<modelVersion>...</modelVersion>
	<groupId>...</groupId>
  	<artifactId>...</artifactId>
  	<version>...</version>
  	<packaging>pom</packaging>
  	<name>Account Aggregator</name>
  	<modules>
		<module>account-email</module>
		<module>account-persist</module>
	</modules>
	xxx.version
</project>
```
**说明：** 

  * 一般`<packaging>`都是用jar或者war，但是作为聚合，这里必须使用pom，其实只要是父模块不是叶子模块就必须打包成`pom`。
  * `<module>`指定要聚合的模块，使用的是当前模块的相对路径
  * `<name>`标签可以在构建的时候更清晰地输出信息 

## MVN继承
**场景：**开发的过程中很多模块可能会用到相同的`pom`配置，因此导致在项目构建中出现很多重复的配置信息，而事实告诉我们重复越多在修改的过程中越容易出现问题；并且不同的模块由不同的人去开发，所用到的一些依赖版本很容易出现不一致而导致运行冲突，因此MVN提供了继承来解决这个问题。

继承类似于聚合，也是需要建立一个只包含`pom`文件的空模块（如：account-parent），而后通过设置子模块（account-email）中的`<parent>`标签来使子模块继承父模块。可以继承的东西有很多，如**依赖、版本号、groupId、插件等**。 account-email实例如下：

```
<parent>
	<groupId>...</groupId>
	<artifactId>...</artifactId>
	<version>...</version>
	<relativePath>../account-parent/pom.xml</relativePath>
</parent>

```

**说明：**

* `<relativePath>`标签指定继承`pom`的相对路径，默认会子模块上一级文件夹里去找，没指定或者没找到的话则到本地仓库中去找。

## MVN BOM+Dependency

你会发现**继承模块**和**聚合模块**是很相似的两个模块，其实这两个东西是可以通用的，也就是说一个父模块既可以聚合，同时也能作为子模块的依赖，这就是目前很多Spring项目用到的`Bom+Dependecy`方法。

`Bom`可以理解成针对某个特定任务或者框架所需要用到的第三方依赖包，在父模块`pom`文件中直接`import`这个`Bom`包就可以使继承它的所有子模块使用其中的依赖关系。`Bom`的加载实例如下：

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-framework-bom</artifactId>
            <version>4.0.1.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

MVN的聚合和继承变化很灵活，下图演示两种常用使用方法：

![mvn Inh+Aggre](https://github.com/SuperemeFrank/gitRepo.git/mvn_Inh+Aggre.png)