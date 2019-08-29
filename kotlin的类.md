## kotlin的类

[TOC]

### 一、前言

先看一个`Java` 类

```java
class Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

用`kotlin`如何定义类

```kotlin
class Person(var name: String, var age: Int)
```



* 相同点：都是用`class`来的定义
* 不同点：定义属性的方式不同，kotlin也不需要写get、set，代码量明显减少

这里可以看到`kotlin`的第一个优点：**`简洁`**

那么kotlin是如何做到简洁的呢，且看下文，听我细细讲来



### 二、kotlin 类解析

`kotlin`类的解析过程：

`*.kt` --> `*.class`--> `jvm`

Android studio中`kotlin bytecode`工具使用：

`Android studio菜单栏Tools` --> `Kotlin` --> `Show Kotlin ByteCode` --> `Decompile` --> 转化成Java代码

下面我们来看下通过上述工具`Kotlin 代码`转化成`Java 代码`是什么样

* Kotlin 代码

  ```kotlin
  class Person(var name: String, var age: Int)
  ```

* Java 代码

  ```java
  import kotlin.Metadata;
  import kotlin.jvm.internal.Intrinsics;
  import org.jetbrains.annotations.NotNull;
  
  @Metadata(
     mv = {1, 1, 15},
     bv = {1, 0, 3},
     k = 1,
     d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"},
     d2 = {"Lcom/panmin/awesomekotlin/kotlin/Person;", "", "name", "", "age", "", "(Ljava/lang/String;I)V", "getAge", "()I", "setAge", "(I)V", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "app_debug"}
  )
  public final class Person {
     @NotNull
     private String name;
     private int age;
  
     @NotNull
     public final String getName() {
        return this.name;
     }
  
     public final void setName(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.name = var1;
     }
  
     public final int getAge() {
        return this.age;
     }
  
     public final void setAge(int var1) {
        this.age = var1;
     }
  
     public Person(@NotNull String name, int age) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        super();
        this.name = name;
        this.age = age;
     }
  }
  ```

  下面我们来看看`kotlin`帮我们做了什么，才使得代码变得这么**`简洁`**

#### 2.1 Metadata注解源码解析

我们跟进metadata的源码看看，这个东西是做什么的

```kotlin
package kotlin

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@SinceKotlin("1.3")
public annotation class Metadata(
    @get:JvmName("k")
    val kind: Int = 1,

    @get:JvmName("mv")
    val metadataVersion: IntArray = [],

    @get:JvmName("bv")
    val bytecodeVersion: IntArray = [],

    @get:JvmName("d1")
    val data1: Array<String> = [],

    @get:JvmName("d2")
    val data2: Array<String> = [],

    @get:JvmName("xs")
    val extraString: String = "",

    @SinceKotlin("1.2")
    @get:JvmName("pn")
    val packageName: String = "",

    @SinceKotlin("1.1")
    @get:JvmName("xi")
    val extraInt: Int = 0
)
```

从源码中可以看出，这个注解是专属于`kotlin`的（java是没有这个注解的），metadata属于`运行时注解`而且是针对`class`的注解；为了减少字符串所占的长度为每个属性设置了`JvmName`简称，下面来看下每个属性都是代表什么含义。

* **kind**：简称`k`，Int类型，表示kotlin文件的类型

  * 1：Class表示`class`或者`interface`类型

    ```kotlin
    interface KotlinInterface
    class KotlinClass
    ```

    转成Java代码是：

    ```java
    @Metadata(
       ......
       k = 1,
    	 ......
    )
    public interface KotlinInterface {
    }
    
    @Metadata(
    	 ......
       k = 1,
       ......
    )
    public final class KotlinClass {
    }
    ```

  * 2：File表示这是一个以`.kt`结尾的`kotlin`文件

    ```koltin
    const val TAG = "TAG"
    ```

    ```java
    @Metadata(
    	 ......
       k = 2,
       ......
    )
    public final class DataKt {
       @NotNull
       public static final String TAG = "TAG";
    }
    ```

  * 3：Synthetic class表示这这是一个kotlin合成类

  * 4：Multi-file class facade

  * 5：Multi-file class part

* **metadataVersion**：简称`mv`，表示metadata的版本号，如：

  ```java
  mv = {1, 1, 15},
  ```

  表示metadata的版本号是：**1.1.15**

* **bytecodeVersion**：简称`bv`，表示字节码的版本号，如：

  ```java
  bv = {1, 0, 3},
  ```

  表示字节码版本号是：**1.0.3**

* **data1**：简称`d1`，主要记录kotlin的语法信息

* **data2**：简称`d2`，主要记录kotlin的语法信息

* **extraString**：简称`xs`，主要是为多文件的类(Multi-file class)预留的名称

* **packageName**：简称`pn`，主要记录kotlin类完整的包名，从kotlin1.2之后才有该属性

* **extraInt**：简称`xi`，表示一下flags：

  * 0：表示一个多文件的类Multi-file class facade或者多文件类的部分Multi-file class part编译成-Xmultifile-parts-inherit
  * 1：表示此类文件由Kotlin的预发行版本编译，并且对于发行版本不可见
  * 2：表示这个类文件是一个编译的Kotlin脚本源文件



#### 2.2  class的构造函数

* **主构造函数**

  ```kotlin
  class KotlinClass(
      p1: String,
      p2: String,
      var p3: String,
      val p4: String,
      private var p5: String,
      private val p6: String
  ) {
      private var merge: String? = null
      init {
          merge = p1 + p2
      }
      fun f() {
  //        p1，p2 无法访问
  //        //p3，p6 无法赋值
  //        p4，p5 正常赋值
      }
  }
  ```

  `kotlin`的构造函数放在头部，下面来看看每个参数的不同定义方式的区别：

  * 未加修饰符的参数：只能在`init`中访问，如上面的p1和p2

  * 未添加`private`修饰符的参数：除了能在类内部使用，还能在实例化之后访问，如p3，p4

    ```kotlin
    val kotlinClass = KotlinClass("p1", "p2", "p3", "p4", "p5", "p6")
    kotlinClass.p3 = "aaa"
    //kotlinClass.p4 = "bbb" 只能get，不能set赋值
    ```

    * **`var`**：可变的变量
    * **`val`**：不可变的变量

  * 添加了`private`修饰符的参数：只能在类内部使用

* **次级构造函数**

  > 一个类当然会有多个构造函数的可能，只有主构造函数可以写在类头中，其他的次级构造函数(Secondary Constructors)就需要写在类体中了

  ```kotlin
  class KotlinClass2(var p1:String){
      private var p2:String? = null
      constructor(p1: String,p2: String):this(p1){
          this.p2 = p2
      }
  }
  ```

  `注意p2没有修饰符`

* **带默认值的构造函数**

  ```kotlin
  class KotlinClass3(var p1:String="a")
  //实例化时有三种方式
  fun main(args: Array<String>) {
      KotlinClass3()
      KotlinClass3("")
      KotlinClass3(p1 = "")
  }
  ```

### 三、class的修饰符

#### 3.1 open修饰符

`Kotlin` 默认会为每个变量和方法添加` final `修饰符。这么做的目的是为了程序运行的性能，其实在 Java 程序中，你也应该尽可能为每个类添加`final`修饰符。

为每个类加了`final`也就是说，在 Kotlin 中默认每个类都是不可被继承的。如果你确定这个类是会被继承的，那么你需要给这个类添加 `open` 修饰符。

#### 3.2 internal 修饰符

> Java 有3种访问修饰符：public、private、protected，还有一个默认的包级别访问权限没有修饰符

在 Kotlin 中，默认的访问权限是 public，也就是说不加访问权限修饰符的就是 public 的。而多增加了一种访问修饰符叫 `internal`。它是`module`级别的访问权限。

当跨 `module` 的时候就无法访问另一个`module` 的 `internal` 变量或方法。



### 四、常见的特殊类

#### 4.1 data数据类

先看下代码，加data之后多了哪些方法

```kotlin
data class KotlinData(var p1:String)
```

Decompile后的Java类：

```java
public final class KotlinData {
	 ......
	 @NotNull
   public final KotlinData copy(@NotNull String p1) { ...... }
   @NotNull
   public String toString() { ...... }
   public int hashCode() { ...... }
   public boolean equals(@Nullable Object var1) { ...... }
}
```

#### 4.2 sealed 密封类

sealed 修饰的类称为密封类，用来表示受限的类层次结构。例如当一个值为有限集中的 类型、而不能有任何其他类型时。在某种意义上,他们是枚举类的扩展:枚举类型的值集合也是受限的，但每个枚举常量只存在一个实例,而密封类的一个子类可以有可包含状态的多个实例。

#### 4.3 枚举类

在 Kotlin 中，每个枚举常量都是一个对象。枚举常量用逗号分隔。

```
enum class EnumClass { A,B,C;}
```

Decompile转化后的Java类：

```java
public enum EnumClass {
   A,
   B,
   C;
}
```

#### 4.4 inner 内部类

```kotlin
class Outter{
    val testVal = "test"
    inner class Inner{
        fun execute(){
            Log.d("test", "Inner -> execute : can read testVal=$testVal")
        }
    }
}
```

内部类能够访问外部类的属性和成员变量



### 五、kotlin类的特性

#### 5.1 类的扩展

> 在Java开发中，我们经常在common module中添加各种Utils类，在kotlin中我们还继续这个思路写一大堆Utils吗？

##### 5.1.1 扩展方法

如在Java中封装toast提示：

```java
public final class ToastUtils {
		public static void show(String msg) {
			  Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}
```

在kotlin中该如何封装呢？

```kotlin
fun Activity.show(msg:String,duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, msg, duration).show()
}
```

##### 5.1.2 强转与智能转换

在 `Kotlin` 中，用 is 来判断一个对象是否是某个类的实例，用 as 来做强转

```kotlin
override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
  with(sp.edit()) {
    when (value) {
      is Boolean -> putBoolean(key, value)
      is Int -> putInt(key, value)
      is String -> putString(key, value)
      is Float -> putFloat(key, value)
      is Long -> putLong(key, value)
      else -> throw Throwable("not support this type")
    }
  }.apply()
}
override fun getValue(thisRef: Any, property: KProperty<*>): T {
  return sp.let {
    when (default) {
      is Boolean -> it.getBoolean(key, default)
      is Int -> it.getInt(key, default)
      is String -> it.getString(key, default)
      is Float -> it.getFloat(key, default)
      is Long -> it.getLong(key, default)
      else -> throw Throwable("not support this type")
    }
  } as T
}
```

#### 5.2 伴生对象

> 由于 `Kotlin` 没有静态方法。在大多数情况下，官方建议是简单地使用 包级 函数。如果你需要写一个可以无需用一个类的实例来调用、但需要访问类内部的函数(例如,工厂方法或单例)，你可以把它写成一个用 `companion`修饰的对象内的方法。

```kotlin
class RouterPath {
    companion object {
        const val MAIN_ACTIVITY = "/app/MainActivity"
        const val LOGIN_ACTIVITY = "/login/LoginActivity"
    }
    fun HomePageRouterMap() = mapOf(
      APP_HOME to 0,
			......
  )
}
```

#### 5.3 单例类

伴生对象更多的用途是用来创建一个单例类。如果只是简单的写，直接用伴生对象返回一个 `val` 修饰的外部类对象就可以了，但是更多的时候我们希望在类被调用的时候才去初始化他的对象。以下代码将线程安全问题交给虚拟机在静态内部类加载时处理，是一种推荐的写法：

```kotlin
class Single private constructor() {
    companion object {
        fun get():Single{
            return Holder.instance
        }
    }

    private object Holder {
        val instance = Single()
    }
}
```









