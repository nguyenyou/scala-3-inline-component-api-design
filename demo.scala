package demo

enum Variant {
  case Primary, Secondary
}

enum Size {
  case Small, Medium, Large
}

enum IconName {
  case Home, Search, Settings, Empty
}

type VariantSelector = Variant.type => Variant
type SizeSelector = Size.type => Size
type IconNameSelector = IconName.type => IconName

case class Button(variant: Variant, size: Size, icon: IconName)

object Button {
  inline def apply(
      inline variant: VariantSelector = _.Primary,
      inline size: SizeSelector = _.Medium,
      inline icon: IconNameSelector = _.Empty
  ): Button = {
    Button(
      variant = variant(Variant),
      size = size(Size),
      icon = icon(IconName)
    )
  }
}

// Test 1: Using implicit def (this works)
object Test1 {
  inline implicit def variantToVariantSelector(x: Variant): VariantSelector =
    (_: Variant.type) => x

  @main def run1() = {
    val button = Button(variant = Variant.Secondary, icon = _.Home)
    println(s"Test1: $button")
  }
}

// Test 2: Using given Conversion (this doesn't work)
object Test2 {
  inline given Conversion[Variant, VariantSelector] with {
    inline def apply(x: Variant): VariantSelector = { (_: Variant.type) =>
      x
    }
  }

  @main def run2() = {
    val button = Button(variant = Variant.Secondary, icon = _.Home)
    println(s"Test2: $button")
  }
}

// Test 3: Using given Conversion without inline (for comparison)
object Test3 {
  given Conversion[Variant, VariantSelector] with {
    def apply(x: Variant): VariantSelector = { (_: Variant.type) =>
      x
    }
  }

  @main def run3() = {
    val button = Button(variant = Variant.Secondary, icon = _.Home)
    println(s"Test3: $button")
  }
}
