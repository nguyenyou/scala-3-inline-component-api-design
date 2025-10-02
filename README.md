## Component API Design

```scala
package main

enum Variant {
  case Primary, Secondary
}

enum Size {
  case Small, Medium, Large
}

type VariantSelector = Variant.type => Variant
type SizeSelector = Size.type => Size

case class Button(variant: Variant, size: Size)

inline def defaultVariant: VariantSelector = _.Primary
inline def defaultSize: SizeSelector = _.Medium

object Button {
  transparent inline def apply(
      inline variant: VariantSelector = defaultVariant,
      inline size: SizeSelector = defaultSize
  ): Button = {
    Button(variant = variant(Variant), size = size(Size))
  }
}

@main def run() = {
  val button1 = Button()
  val button2 = Button(variant = _.Secondary)
  val button3 = Button(variant = _.Secondary, size = _.Large)
  println(button1)
  println(button2)
  println(button3)
}

```

Run:

```
scalac -Xprint:inline main.scala
```

Output:

```scala
[[syntax trees at end of          inlinedPositions]] // main.scala
package main {
  @Child[(main.Variant.Secondary : main.Variant)] @Child[
    (main.Variant.Primary : main.Variant)] sealed abstract class Variant()
     extends Object(), scala.reflect.Enum {
    import main.Variant.{Primary, Secondary}
  }
  final lazy module val Variant: main.Variant = new main.Variant()
  final module class Variant() extends AnyRef() { this: main.Variant.type =>
    case <static> val Primary: main.Variant = main.Variant.$new(0, "Primary")
    case <static> val Secondary: main.Variant =
      main.Variant.$new(1, "Secondary")
    private[this] val $values: Array[main.Variant] =
      Array.apply[main.Variant]([this.Primary,this.Secondary : main.Variant]*)(
        using scala.reflect.ClassTag.apply[main.Variant](classOf[main.Variant]))
    def values: Array[main.Variant] = main.Variant.$values.clone()
    def valueOf($name: String): main.Variant =
      $name match
        {
          case "Primary" => this.Primary
          case "Secondary" => this.Secondary
          case _ =>
            throw
              new IllegalArgumentException(
                "enum main.Variant has no case with name: " + $name)
        }
    private[this] def $new(_$ordinal: Int, $name: String): main.Variant =
      {
        final class $anon() extends main.Variant(), _root_.scala.runtime.
          EnumValue {}
        new $anon():(main.Variant & scala.runtime.EnumValue)
      }
    def fromOrdinal(ordinal: Int): main.Variant =
      try main.Variant.$values.apply(ordinal) catch
        {
          case _ =>
            throw
              new NoSuchElementException(
                "enum main.Variant has no case with ordinal: " +
                  ordinal.toString()
              )
        }
  }
  @Child[(main.Size.Large : main.Size)] @Child[(main.Size.Medium : main.Size)]
    @Child[(main.Size.Small : main.Size)] sealed abstract class Size() extends
    Object(), scala.reflect.Enum {
    import main.Size.{Small, Medium, Large}
  }
  final lazy module val Size: main.Size = new main.Size()
  final module class Size() extends AnyRef() { this: main.Size.type =>
    case <static> val Small: main.Size = main.Size.$new(0, "Small")
    case <static> val Medium: main.Size = main.Size.$new(1, "Medium")
    case <static> val Large: main.Size = main.Size.$new(2, "Large")
    private[this] val $values: Array[main.Size] =
      Array.apply[main.Size]([this.Small,this.Medium,this.Large : main.Size]*)(
        using scala.reflect.ClassTag.apply[main.Size](classOf[main.Size]))
    def values: Array[main.Size] = main.Size.$values.clone()
    def valueOf($name: String): main.Size =
      $name match
        {
          case "Small" => this.Small
          case "Medium" => this.Medium
          case "Large" => this.Large
          case _ =>
            throw
              new IllegalArgumentException(
                "enum main.Size has no case with name: " + $name)
        }
    private[this] def $new(_$ordinal: Int, $name: String): main.Size =
      {
        final class $anon() extends main.Size(), _root_.scala.runtime.EnumValue
           {}
        new $anon():(main.Size & scala.runtime.EnumValue)
      }
    def fromOrdinal(ordinal: Int): main.Size =
      try main.Size.$values.apply(ordinal) catch
        {
          case _ =>
            throw
              new NoSuchElementException(
                "enum main.Size has no case with ordinal: " + ordinal.toString()
                )
        }
  }
  case class Button(variant: main.Variant, size: main.Size) extends Object(),
    _root_.scala.Product, _root_.scala.Serializable {
    val variant: main.Variant
    val size: main.Size
    def copy(variant: main.Variant, size: main.Size): main.Button =
      new main.Button(variant, size)
    def copy$default$1: main.Variant @uncheckedVariance = Button.this.variant
    def copy$default$2: main.Size @uncheckedVariance = Button.this.size
    def _1: main.Variant = this.variant
    def _2: main.Size = this.size
  }
  final lazy module val Button: main.Button = new main.Button()
  final module class Button() extends Object() { this: main.Button.type =>
    def apply(variant: main.Variant, size: main.Size): main.Button =
      new main.Button(variant, size)
    def unapply(x$1: main.Button): main.Button = x$1
    inline transparent def apply(inline variant: main.VariantSelector,
      inline size: main.SizeSelector): main.Button =
      {
        main.Button.apply(variant = variant.apply(main.Variant),
          size = size.apply(main.Size))
      }
    inline def apply$default$1: main.VariantSelector =
      main.defaultVariant:main.VariantSelector
    inline def apply$default$2: main.SizeSelector =
      main.defaultSize:main.SizeSelector
  }
  final lazy module val main$package: main.main$package =
    new main.main$package()
  final module class main$package() extends Object() {
    this: main.main$package.type =>
    type VariantSelector = main.Variant.type => main.Variant
    type SizeSelector = main.Size.type => main.Size
    inline def defaultVariant: main.VariantSelector =
      (_$1: main.Variant.type) => _$1.Primary:main.VariantSelector
    inline def defaultSize: main.SizeSelector =
      (_$2: main.Size.type) => _$2.Medium:main.SizeSelector
    @main def run(): Unit =
      {
        val button1: main.Button =
          main.Button.apply(
            variant = main.Button.apply$default$1.apply(main.Variant),
            size = main.Button.apply$default$2.apply(main.Size))
        val button2: main.Button =
          main.Button.apply(
            variant =
              {
                val _$3: main.Variant = main.Variant
                _$3.Secondary
              }
            ,
          size = main.Button.apply$default$2.apply(main.Size))
        val button3: main.Button =
          main.Button.apply(
            variant =
              {
                val _$4: main.Variant = main.Variant
                _$4.Secondary
              }
            ,
            size =
              {
                val _$5: main.Size = main.Size
                _$5.Large
              }
          )
        println(button1)
        println(button2)
        println(button3)
      }
  }
  final class run() extends Object() {
    <static> def main(args: Array[String]): Unit =
      try main.run() catch
        {
          case error @ _:scala.util.CommandLineParser.ParseError =>
            scala.util.CommandLineParser.showError(error)
        }
  }
}

[[syntax trees at end of MegaPhase{crossVersionChecks, firstTransform, checkReentrant, elimPackagePrefixes, cookComments, checkLoopingImplicits, betaReduce, inlineVals, expandSAMs, elimRepeated, refchecks, dropForMap}]] // main.scala
package main {
  @SourceFile("main.scala") @Child[(main.Variant.Secondary : main.Variant)]
    @Child[(main.Variant.Primary : main.Variant)] sealed abstract class Variant
    () extends Object(), scala.reflect.Enum {
    import main.Variant.{Primary, Secondary}
  }
  final lazy module val Variant: main.Variant = new main.Variant()
  @SourceFile("main.scala") final module class Variant() extends AnyRef(),
    scala.deriving.Mirror.Sum {
    private def writeReplace(): AnyRef =
      new scala.runtime.ModuleSerializationProxy(classOf[main.Variant.type])
    case <static> val Primary: main.Variant = main.Variant.$new(0, "Primary")
    case <static> val Secondary: main.Variant =
      main.Variant.$new(1, "Secondary")
    private[this] val $values: Array[main.Variant] =
      Array.apply[main.Variant]([this.Primary,this.Secondary : main.Variant])(
        using scala.reflect.ClassTag.apply[main.Variant](classOf[main.Variant]))
    def values: Array[main.Variant] = main.Variant.$values.clone()
    def valueOf($name: String): main.Variant =
      $name match
        {
          case "Primary" => this.Primary
          case "Secondary" => this.Secondary
          case _ =>
            throw
              new IllegalArgumentException(
                "enum main.Variant has no case with name: " + $name)
        }
    private[this] def $new(_$ordinal: Int, $name: String): main.Variant =
      {
        final class $anon() extends main.Variant(), scala.runtime.EnumValue,
          scala.deriving.Mirror.Singleton {
          private def readResolve(): AnyRef =
            main.Variant.fromOrdinal(this.ordinal)
          override def productPrefix: String = $name
          override def toString(): String = $name
          override def ordinal: Int = _$ordinal
        }
        new
          main.Variant with scala.runtime.EnumValue with
            scala.deriving.Mirror.Singleton {...}
        ():(main.Variant & scala.runtime.EnumValue)
      }
    def fromOrdinal(ordinal: Int): main.Variant =
      try main.Variant.$values.apply(ordinal) catch
        {
          case _ =>
            throw
              new NoSuchElementException(
                "enum main.Variant has no case with ordinal: " +
                  ordinal.toString()
              )
        }
    type MirroredMonoType = main.Variant
    def ordinal(x$0: main.Variant.MirroredMonoType): Int = x$0.ordinal
  }
  @SourceFile("main.scala") @Child[(main.Size.Large : main.Size)]
    @Child[(main.Size.Medium : main.Size)] @Child[(main.Size.Small : main.Size)]
     sealed abstract class Size() extends Object(), scala.reflect.Enum {
    import main.Size.{Small, Medium, Large}
  }
  final lazy module val Size: main.Size = new main.Size()
  @SourceFile("main.scala") final module class Size() extends AnyRef(),
    scala.deriving.Mirror.Sum {
    private def writeReplace(): AnyRef =
      new scala.runtime.ModuleSerializationProxy(classOf[main.Size.type])
    case <static> val Small: main.Size = main.Size.$new(0, "Small")
    case <static> val Medium: main.Size = main.Size.$new(1, "Medium")
    case <static> val Large: main.Size = main.Size.$new(2, "Large")
    private[this] val $values: Array[main.Size] =
      Array.apply[main.Size]([this.Small,this.Medium,this.Large : main.Size])(
        using scala.reflect.ClassTag.apply[main.Size](classOf[main.Size]))
    def values: Array[main.Size] = main.Size.$values.clone()
    def valueOf($name: String): main.Size =
      $name match
        {
          case "Small" => this.Small
          case "Medium" => this.Medium
          case "Large" => this.Large
          case _ =>
            throw
              new IllegalArgumentException(
                "enum main.Size has no case with name: " + $name)
        }
    private[this] def $new(_$ordinal: Int, $name: String): main.Size =
      {
        final class $anon() extends main.Size(), scala.runtime.EnumValue,
          scala.deriving.Mirror.Singleton {
          private def readResolve(): AnyRef =
            main.Size.fromOrdinal(this.ordinal)
          override def productPrefix: String = $name
          override def toString(): String = $name
          override def ordinal: Int = _$ordinal
        }
        new
          main.Size with scala.runtime.EnumValue with
            scala.deriving.Mirror.Singleton {...}
        ():(main.Size & scala.runtime.EnumValue)
      }
    def fromOrdinal(ordinal: Int): main.Size =
      try main.Size.$values.apply(ordinal) catch
        {
          case _ =>
            throw
              new NoSuchElementException(
                "enum main.Size has no case with ordinal: " + ordinal.toString()
                )
        }
    type MirroredMonoType = main.Size
    def ordinal(x$0: main.Size.MirroredMonoType): Int = x$0.ordinal
  }
  @SourceFile("main.scala") case class Button(variant: main.Variant,
    size: main.Size) extends Object(), Product, Serializable {
    override def hashCode(): Int =
      scala.util.hashing.MurmurHash3.productHash(this, -70211317, true)
    override def equals(x$0: Any): Boolean =
      (this eq x$0.$asInstanceOf[Object]) ||
        (x$0 match
          {
            case x$0 @ _:main.Button @unchecked =>
              this.variant == x$0.variant && this.size == x$0.size &&
                x$0.canEqual(this)
            case _ => false
          }
        )
    override def toString(): String = scala.runtime.ScalaRunTime._toString(this)
    override def canEqual(that: Any): Boolean =
      that.isInstanceOf[main.Button @unchecked]
    override def productArity: Int = 2
    override def productPrefix: String = "Button"
    override def productElement(n: Int): Any =
      n match
        {
          case 0 => this._1
          case 1 => this._2
          case _ => throw new IndexOutOfBoundsException(n.toString())
        }
    override def productElementName(n: Int): String =
      n match
        {
          case 0 => "variant"
          case 1 => "size"
          case _ => throw new IndexOutOfBoundsException(n.toString())
        }
    val variant: main.Variant
    val size: main.Size
    def copy(variant: main.Variant, size: main.Size): main.Button =
      new main.Button(variant, size)
    def copy$default$1: main.Variant @uncheckedVariance = Button.this.variant
    def copy$default$2: main.Size @uncheckedVariance = Button.this.size
    def _1: main.Variant = this.variant
    def _2: main.Size = this.size
  }
  final lazy module val Button: main.Button = new main.Button()
  @SourceFile("main.scala") final module class Button() extends Object(),
    scala.deriving.Mirror.Product {
    private def writeReplace(): AnyRef =
      new scala.runtime.ModuleSerializationProxy(classOf[main.Button.type])
    def apply(variant: main.Variant, size: main.Size): main.Button =
      new main.Button(variant, size)
    def unapply(x$1: main.Button): main.Button = x$1
    inline transparent def apply(inline variant: main.VariantSelector,
      inline size: main.SizeSelector): main.Button =
      {
        main.Button.apply(variant.apply(main.Variant), size.apply(main.Size))
      }
    inline def apply$default$1: main.VariantSelector =
      main.defaultVariant:main.VariantSelector
    inline def apply$default$2: main.SizeSelector =
      main.defaultSize:main.SizeSelector
    type MirroredMonoType = main.Button
    def fromProduct(x$0: Product): main.Button.MirroredMonoType =
      {
        val variant$1: main.Variant =
          x$0.productElement(0).$asInstanceOf[main.Variant]
        val size$1: main.Size = x$0.productElement(1).$asInstanceOf[main.Size]
        new main.Button(variant$1, size$1)
      }
  }
  final lazy module val main$package: main.main$package =
    new main.main$package()
  @SourceFile("main.scala") final module class main$package() extends Object() {
    private def writeReplace(): AnyRef =
      new scala.runtime.ModuleSerializationProxy(classOf[main.main$package.type]
        )
    type VariantSelector = main.Variant.type => main.Variant
    type SizeSelector = main.Size.type => main.Size
    inline def defaultVariant: main.VariantSelector =
      (_$1: main.Variant.type) => _$1.Primary:main.VariantSelector
    inline def defaultSize: main.SizeSelector =
      (_$2: main.Size.type) => _$2.Medium:main.SizeSelector
    @main def run(): Unit =
      {
        val button1: main.Button =
          main.Button.apply(
            {
              val _$1: main.Variant = main.Variant
              _$1.Primary
            },
            {
              val _$2: main.Size = main.Size
              _$2.Medium
            }
          )
        val button2: main.Button =
          main.Button.apply(
            {
              val _$3: main.Variant = main.Variant
              _$3.Secondary
            },
            {
              val _$2: main.Size = main.Size
              _$2.Medium
            }
          )
        val button3: main.Button =
          main.Button.apply(
            {
              val _$4: main.Variant = main.Variant
              _$4.Secondary
            },
            {
              val _$5: main.Size = main.Size
              _$5.Large
            }
          )
        println(button1)
        println(button2)
        println(button3)
      }
  }
  @SourceFile("main.scala") final class run() extends Object() {
    <static> def main(args: Array[String]): Unit =
      try main.run() catch
        {
          case error @ _:scala.util.CommandLineParser.ParseError =>
            scala.util.CommandLineParser.showError(error)
        }
  }
}

[[syntax trees at end of MegaPhase{pruneErasedDefs, uninitialized, inlinePatterns, vcInlineMethods, seqLiterals, intercepted, getters, specializeFunctions, specializeTuples, collectNullableFields, elimOuterSelect, resolveSuper, functionXXLForwarders, paramForwarding, genericTuples, letOverApply, arrayConstructors}]] // main.scala
package main {
  @SourceFile("main.scala") @Child[(main.Variant.Secondary : main.Variant)]
    @Child[(main.Variant.Primary : main.Variant)] sealed abstract class Variant
    () extends Object(), scala.reflect.Enum {
    import main.Variant.{Primary, Secondary}
  }
  final lazy module val Variant: main.Variant = new main.Variant()
  @SourceFile("main.scala") final module class Variant() extends AnyRef(),
    scala.deriving.Mirror.Sum {
    private def writeReplace(): AnyRef =
      new scala.runtime.ModuleSerializationProxy(classOf[main.Variant.type])
    case <static> val Primary: main.Variant = main.Variant.$new(0, "Primary")
    case <static> val Secondary: main.Variant =
      main.Variant.$new(1, "Secondary")
    private val $values: Array[main.Variant] =
      Array.apply[main.Variant](
        scala.runtime.ScalaRunTime.wrapRefArray[main.Variant](
          [this.Primary,this.Secondary : main.Variant])
      )(using scala.reflect.ClassTag.apply[main.Variant](classOf[main.Variant]))
    def values: Array[main.Variant] = main.Variant.$values.clone()
    def valueOf($name: String): main.Variant =
      matchResult1[(main.Variant.Primary : main.Variant) |
        (main.Variant.Secondary : main.Variant)]:
        {
          case val x1: ($name : String) = $name
          if "Primary" == x1 then return[matchResult1] this.Primary else ()
          if "Secondary" == x1 then return[matchResult1] this.Secondary else ()
          throw
            new IllegalArgumentException(
              "enum main.Variant has no case with name: " + $name)
        }
    private def $new(_$ordinal: Int, $name: String): main.Variant =
      {
        final class $anon() extends main.Variant(), scala.runtime.EnumValue,
          scala.deriving.Mirror.Singleton {
          private def readResolve(): AnyRef =
            main.Variant.fromOrdinal(this.ordinal)
          override def productPrefix: String = $name
          override def toString(): String = $name
          override def ordinal: Int = _$ordinal
        }
        new
          main.Variant with scala.runtime.EnumValue with
            scala.deriving.Mirror.Singleton {...}
        ():(main.Variant & scala.runtime.EnumValue)
      }
    def fromOrdinal(ordinal: Int): main.Variant =
      try main.Variant.$values.apply(ordinal) catch
        {
          case _ =>
            throw
              new NoSuchElementException(
                "enum main.Variant has no case with ordinal: " +
                  ordinal.toString()
              )
        }
    type MirroredMonoType = main.Variant
    def ordinal(x$0: main.Variant.MirroredMonoType): Int = x$0.ordinal
  }
  @SourceFile("main.scala") @Child[(main.Size.Large : main.Size)]
    @Child[(main.Size.Medium : main.Size)] @Child[(main.Size.Small : main.Size)]
     sealed abstract class Size() extends Object(), scala.reflect.Enum {
    import main.Size.{Small, Medium, Large}
  }
  final lazy module val Size: main.Size = new main.Size()
  @SourceFile("main.scala") final module class Size() extends AnyRef(),
    scala.deriving.Mirror.Sum {
    private def writeReplace(): AnyRef =
      new scala.runtime.ModuleSerializationProxy(classOf[main.Size.type])
    case <static> val Small: main.Size = main.Size.$new(0, "Small")
    case <static> val Medium: main.Size = main.Size.$new(1, "Medium")
    case <static> val Large: main.Size = main.Size.$new(2, "Large")
    private val $values: Array[main.Size] =
      Array.apply[main.Size](
        scala.runtime.ScalaRunTime.wrapRefArray[main.Size](
          [this.Small,this.Medium,this.Large : main.Size])
      )(using scala.reflect.ClassTag.apply[main.Size](classOf[main.Size]))
    def values: Array[main.Size] = main.Size.$values.clone()
    def valueOf($name: String): main.Size =
      matchResult2[(main.Size.Small : main.Size) |
        (main.Size.Medium : main.Size) | (main.Size.Large : main.Size)]:
        {
          case val x2: ($name : String) = $name
          x2 match
            {
              case "Small" => return[matchResult2] this.Small
              case "Medium" => return[matchResult2] this.Medium
              case "Large" => return[matchResult2] this.Large
              case _ =>
                throw
                  new IllegalArgumentException(
                    "enum main.Size has no case with name: " + $name)
            }
        }
    private def $new(_$ordinal: Int, $name: String): main.Size =
      {
        final class $anon() extends main.Size(), scala.runtime.EnumValue,
          scala.deriving.Mirror.Singleton {
          private def readResolve(): AnyRef =
            main.Size.fromOrdinal(this.ordinal)
          override def productPrefix: String = $name
          override def toString(): String = $name
          override def ordinal: Int = _$ordinal
        }
        new
          main.Size with scala.runtime.EnumValue with
            scala.deriving.Mirror.Singleton {...}
        ():(main.Size & scala.runtime.EnumValue)
      }
    def fromOrdinal(ordinal: Int): main.Size =
      try main.Size.$values.apply(ordinal) catch
        {
          case _ =>
            throw
              new NoSuchElementException(
                "enum main.Size has no case with ordinal: " + ordinal.toString()
                )
        }
    type MirroredMonoType = main.Size
    def ordinal(x$0: main.Size.MirroredMonoType): Int = x$0.ordinal
  }
  @SourceFile("main.scala") case class Button(variant: main.Variant,
    size: main.Size) extends Object(), Product, Serializable {
    override def hashCode(): Int =
      scala.util.hashing.MurmurHash3.productHash(this, -70211317, true)
    override def equals(x$0: Any): Boolean =
      (this eq x$0.$asInstanceOf[Object]) ||
        (matchResult3[Boolean]:
          {
            case val x3: (x$0 : Any) = x$0
            if x3.$isInstanceOf[main.Button @unchecked] then
              {
                case val x$0: main.Button =
                  x3.$asInstanceOf[main.Button @unchecked]
                return[matchResult3]
                  this.variant == x$0.variant && this.size == x$0.size &&
                    x$0.canEqual(this)
              }
             else ()
            return[matchResult3] false
          }
        )
    override def toString(): String = scala.runtime.ScalaRunTime._toString(this)
    override def canEqual(that: Any): Boolean =
      that.isInstanceOf[main.Button @unchecked]
    override def productArity: Int = 2
    override def productPrefix: String = "Button"
    override def productElement(n: Int): Any =
      matchResult4[main.Variant | main.Size]:
        {
          case val x5: (n : Int) = n
          if 0 == x5 then return[matchResult4] this._1 else ()
          if 1 == x5 then return[matchResult4] this._2 else ()
          throw new IndexOutOfBoundsException(n.toString())
        }
    override def productElementName(n: Int): String =
      matchResult5[("variant" : String) | ("size" : String)]:
        {
          case val x6: (n : Int) = n
          if 0 == x6 then return[matchResult5] "variant" else ()
          if 1 == x6 then return[matchResult5] "size" else ()
          throw new IndexOutOfBoundsException(n.toString())
        }
    def variant: main.Variant
    def size: main.Size
    def copy(variant: main.Variant, size: main.Size): main.Button =
      new main.Button(variant, size)
    def copy$default$1: main.Variant @uncheckedVariance = Button.this.variant
    def copy$default$2: main.Size @uncheckedVariance = Button.this.size
    def _1: main.Variant = this.variant
    def _2: main.Size = this.size
  }
  final lazy module val Button: main.Button = new main.Button()
  @SourceFile("main.scala") final module class Button() extends Object(),
    scala.deriving.Mirror.Product {
    private def writeReplace(): AnyRef =
      new scala.runtime.ModuleSerializationProxy(classOf[main.Button.type])
    def apply(variant: main.Variant, size: main.Size): main.Button =
      new main.Button(variant, size)
    def unapply(x$1: main.Button): main.Button = x$1
    private inline transparent def apply(inline variant: main.VariantSelector,
      inline size: main.SizeSelector): main.Button =
      scala.compiletime.erasedValue[main.Button]
    private inline def apply$default$1: main.VariantSelector =
      scala.compiletime.erasedValue[main.VariantSelector]
    private inline def apply$default$2: main.SizeSelector =
      scala.compiletime.erasedValue[main.SizeSelector]
    type MirroredMonoType = main.Button
    def fromProduct(x$0: Product): main.Button.MirroredMonoType =
      {
        val variant$1: main.Variant =
          x$0.productElement(0).$asInstanceOf[main.Variant]
        val size$1: main.Size = x$0.productElement(1).$asInstanceOf[main.Size]
        new main.Button(variant$1, size$1)
      }
  }
  final lazy module val main$package: main.main$package =
    new main.main$package()
  @SourceFile("main.scala") final module class main$package() extends Object() {
    private def writeReplace(): AnyRef =
      new scala.runtime.ModuleSerializationProxy(classOf[main.main$package.type]
        )
    type VariantSelector = main.Variant.type => main.Variant
    type SizeSelector = main.Size.type => main.Size
    private inline def defaultVariant: main.VariantSelector =
      scala.compiletime.erasedValue[main.VariantSelector]
    private inline def defaultSize: main.SizeSelector =
      scala.compiletime.erasedValue[main.SizeSelector]
    @main def run(): Unit =
      {
        val button1: main.Button =
          main.Button.apply(
            {
              val _$1: main.Variant = main.Variant
              _$1.Primary
            },
            {
              val _$2: main.Size = main.Size
              _$2.Medium
            }
          )
        val button2: main.Button =
          main.Button.apply(
            {
              val _$3: main.Variant = main.Variant
              _$3.Secondary
            },
            {
              val _$2: main.Size = main.Size
              _$2.Medium
            }
          )
        val button3: main.Button =
          main.Button.apply(
            {
              val _$4: main.Variant = main.Variant
              _$4.Secondary
            },
            {
              val _$5: main.Size = main.Size
              _$5.Large
            }
          )
        println(button1)
        println(button2)
        println(button3)
      }
  }
  @SourceFile("main.scala") final class run() extends Object() {
    <static> def main(args: Array[String]): Unit =
      try main.run() catch
        {
          case error @ _:scala.util.CommandLineParser.ParseError =>
            scala.util.CommandLineParser.showError(error)
        }
  }
}

```

## Explain

This is a sophisticated Component API Design Pattern demonstrating advanced Scala 3 metaprogramming techniques. Let me break down the key concepts:

### Core Design Pattern (Lines 4-29)

#### The Problem Being Solved:

This creates a React/Vue-style component API where you can write:
Button(variant = _.Secondary, size = _.Large)
instead of:
Button(variant = Variant.Secondary, size = Size.Large)

#### Key Components:

1. Type Aliases (Lines 14-15)

type VariantSelector = Variant.type => Variant
type SizeSelector = Size.type => Size
These define function types that take the enum's companion object and return an enum value. This enables the \_.Secondary syntax.

2. Inline Default Functions (Lines 19-20)

inline def defaultVariant: VariantSelector = _.Primary
inline def defaultSize: SizeSelector = _.Medium

- inline tells the compiler to inline these at call sites (compile-time expansion)
- They're functions that select default enum values
- The \_.Primary syntax is sugar for (x: Variant.type) => x.Primary

3. The Magic: Transparent Inline Apply (Lines 23-28)

transparent inline def apply(
inline variant: VariantSelector = defaultVariant,
inline size: SizeSelector = defaultSize
): Button = {
Button(variant = variant(Variant), size = size(Size))
}

Why This Works:

- transparent inline: Erased at runtime, fully expanded at compile-time
- inline parameters: Expanded at the call site
- variant(Variant) invokes the selector function with the enum companion object
- Default parameters use the inline default functions

What the Compiler Output Shows

The compiler output (lines 50-715) reveals the transformation across three compilation phases:

Phase 1: After Inlining (Lines 172-203)

val button1: main.Button =
main.Button.apply(
variant = main.Button.apply$default$1.apply(main.Variant),
      size = main.Button.apply$default$2.apply(main.Size))
Shows how defaults are resolved and selector functions are applied.

Phase 2: After Beta-Reduction (Lines 410-446)

val button1: main.Button =
main.Button.apply(
{
val _$1: main.Variant = main.Variant
_$1.Primary
},
{
val _$2: main.Size = main.Size
_$2.Medium
}
)
The selector functions _.Primary and _.Medium are expanded into explicit code blocks that capture the enum companion and select the value.

Phase 3: After Erasure (Lines 669-679)

The inline transparent methods are completely erased, leaving only the actual enum value selections.

Advanced Scala 3 Features Used

1. Transparent Inline: Methods that exist only at compile-time
2. Inline Parameters: Force compile-time evaluation
3. Eta Expansion: \_.Primary → (x: Variant.type) => x.Primary
4. Type-safe Enum Selection: The selector pattern ensures you can only select valid enum values
5. Zero Runtime Overhead: All abstraction compiled away

Why This Pattern is Powerful

- Type Safety: Can't pass wrong enum types
- Ergonomics: Cleaner API than passing companion objects
- Performance: Zero runtime cost - everything inlined
- IDE Support: Auto-completion works with \_.
- Consistency: Mimics popular component library patterns

This is an excellent example of using Scala 3's metaprogramming capabilities to create elegant DSLs with zero runtime overhead.
