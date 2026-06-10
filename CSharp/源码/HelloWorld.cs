#define DEBUG
#undef DEBUG

namespace StudyConsoleApp;

public static class HelloWorld {
    public static void Main(string[] args) {
        Console.WriteLine("Hello World!");
        Console.Write("oooooaaaa\n");
        // Read方法会返回输入的字符串的第一个字符的unicode编码
        Console.WriteLine(Console.Read());
        // ReadKey方法会读取所按下的按键，并返回一个带着三个枚举属性的对象，它不会等到按回车才终止读取
        // 其中Key表示该键所对应的键盘上的按键，KeyChar表示当前输入的值，Modifiers则表示如果按到了Shift、Alt或者Ctrl，那么返回对应的值，如果没有，那么返回None
        // 如果是开着大写模式按字母键的话，Modifiers也会输出Shift
        Console.WriteLine(Console.ReadKey().Key);
        Console.WriteLine(Console.ReadKey().KeyChar);
        Console.WriteLine(Console.ReadKey().Modifiers);
        Console.Read();
        Console.Read();
        Console.Read();
        Console.WriteLine("输入为:" + Console.ReadLine());
    }

    /*
     * 下面是一个C#的文档注释的样例，C#的文档注释需要使用三个 / 来修饰，并使用标签来表示各个文档含义
     * <summary>标签: 描述类、方法、属性等的简要说明
     * <param>: 方法参数的说明
     * <returns>: 方法返回值的说明
     * <exception>: 方法异常说明
     * <remarks>: 补充说明
     * <example>: 在文档注释中写一些示例代码，写代码的时候需要使用<code>标签包起来
     * <code>: 需要在<example>标签中使用，用于将示例代码包起来
     * <seealso>: 相关的引用参考信息
     * <value>: 描述成员属性的的值
     * <typeparam>: 泛型类型的参数
     */
    /// <summary>
    ///  返回大于10的列表项
    /// </summary>
    /// <param name="list">输入的整数列表</param>
    /// <returns>大于10的列表项</returns>
    /// <exception cref="ArgumentNullException">如果输入列表为null</exception>
    /// <seealso cref="System.Collections.Generic.List{T}"/>
    /// <seealso cref="System.Linq.Enumerable"/>
    public static List<int> GetItemsBiggerThanTen(IEnumerable<int> list) {
        return (
            from item
                in list
            where item > 10
            select item
        ).ToList();
    }

    // 预处理指令专属示例方法
    public static void Preprocessing() {
#if DEBUG
        Console.WriteLine("DEBUG MODE");
#elif RELEASE
        Console.WriteLine("RELEASE MODE");
#else
        Console.WriteLine("Other Mode");
#endif
        // #region和#endregion预处理指令可以自定义折叠代码块，使用后在编辑器左侧就会出现折叠的小按钮
        #region 折叠代码块开始
        Console.WriteLine("Hello World!");
        #endregion
        // #pragma指令用于向编译器传递一些指令，比如让它忽略一些warning
        # pragma warning disable CS0219
        int unUsedInt = 12;
        // #nullable指令默认是开启的，它用来控制是否编译器会弹出将空值赋给不可为空变量的警告
        #nullable disable
        string cannotBeNull = "haha";
        cannotBeNull = null;
        // #warning和#error预处理指令分别用于告诉编译器这是一个警号和错误
        #warning 这是一个警告
        // #error 这是一个错误
        #line 100 "HelloWorld文件"
        throw new Exception("这是第100行");
    }
}