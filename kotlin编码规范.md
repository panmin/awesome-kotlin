## Kotlin编码规范

### 一、源代码的组织

#### 1.1 目录结构

Kotlin源文件应当与Java源文件位于同一源文件目录下，并遵循相同的目录结构，每个文件应存储在与其package语句对应的目录中。

#### 1.2 源文件名称

如果Kotlin文件包含**单个**类或者接口，那么文件名应该和该类名或者接口名**相同**，并追加`.kt`扩展名。

如果文件中包含**多个**类或只包含顶层声明，那么选择一个描述该文件所包含内容的名称，并以此命名该文件。使用**首字母大写**的**驼峰命名**规则。

#### 1.3 源文件组织

鼓励**多个声明**（类、顶级函数或者属性）放在**同一个**Kotlin源文件中，只要这些声明在语义上彼此关联并且文件保持合理大小（不超过几百行）。

参考一个开源的kotlin扩展函数工具类库：[**AndroidUtilCodeKTX**](https://github.com/lulululbj/AndroidUtilCodeKTX)

### 二、命名规则

#### 2.1 类名

以大写字母开头并使用驼峰：

```kotlin
class DeclarationProcessor { …… }

object EmptyDeclarationProcessor : DeclarationProcessor() { …… }
```

#### 2.2 函数名

函数、属性与局部变量的名称以小写字母开头、使用`驼峰`而**不使用下划线**：

```kotlin
fun processDeclarations() { …… }
var declarationCount = ……
```

**例外**：类的扩展方法

```kotlin
fun Activity.show(msg:String,duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, msg, duration).show()
}
```

#### 2.3 属性名

常量名称（标有`const`的属性，或者标有`val`的不可变数据）应该使用大写、下划线分割的名称：

```kotlin
const val MAX_COUNT = 8
val USER_NAME_FIELD = "UserName"
```

保存带有行为的对象或者可变数据的顶层/对象属性的名称应该使用**常规驼峰命名**规则：

```kotlin
val mutableCollection: MutableSet<String> = HashSet()
```

对于枚举常量，可以使用大写、下划线分割的名称，也可以使用大写字母开头的驼峰命名规则：

```kotlin
enum class Color { RED, GREEN }
enum class Status { NormalStatus, UnNormalStatus }
```

### 三、格式化

**1、使用 4 个空格缩进。不要使用 tab。**

对于花括号，将左花括号放在结构起始处的行尾，而将右花括号放在与左括结构横向对齐的单独一行。

```kotlin
if (elements != null) {
    for (element in elements) {
        // ...
    }
}
```

**2、使用Android studio默认自带的kotlin codeStyle进行格式化**

### 五、文档注释

通常，避免使用 `@param` 与 `@return` 标记。而是将参数与返回值的描述直接合并到文档注释中，并在提到参数的任何地方加上参数链接。 只有当需要不适合放进主文本流程的冗长描述时才应使用 `@param` 与 `@return`。

```kotlin
// 避免这样：
/**
 * Returns the absolute value of the given number.
 * @param number The number to return the absolute value for.
 * @return The absolute value.
 */
fun abs(number: Int) = ...

// 而要这样：
/**
 * Returns the absolute value of the given [number].
 */
fun abs(number: Int) = ...
```

### 六、避免重复结构

#### 6.1 Unit

如果函数返回 Unit，那么应该省略返回类型：

```kotlin
fun foo() { // 这里省略了“: Unit”

}
```

#### 6.2 分号

尽可能省略分号。

#### 6.3 字符串模版

将简单变量传入到字符串模版中时不要使用花括号。只有用到更长表达式时才使用花括号。

```kotlin
println("$name has ${children.size} children")
```

### 七、语言特性的习惯写法

#### 7.1 不可变性

优先使用不可变（而不是可变）数据。初始化后未修改的局部变量与属性，总是将其声明为 `val` 而不是 `var` 。

总是使用不可变集合接口（`Collection`, `List`, `Set`, `Map`）来声明无需改变的集合。使用工厂函数创建集合实例时，尽可能选用返回不可变集合类型的函数：

```kotlin
// 不良：使用可变集合类型作为无需改变的值
fun validateValue(actualValue: String, allowedValues: HashSet<String>) { ... }

// 良好：使用不可变集合类型
fun validateValue(actualValue: String, allowedValues: Set<String>) { ... }

// 不良：arrayListOf() 返回 ArrayList<T>，这是一个可变集合类型
val allowedValues = arrayListOf("a", "b", "c")

// 良好：listOf() 返回 List<T>
val allowedValues = listOf("a", "b", "c")
```

#### 7.2 默认参数值

优先声明带有默认参数的函数而不是声明重载函数。

```kotlin
// 不良
fun foo() = foo("a")
fun foo(a: String) { ... }

// 良好
fun foo(a: String = "a") { ... }
```



### 八、库的编码规范