package main

inline implicit def variantToVariantSelector(x: Variant): VariantSelector =
  (_: Variant.type) => x

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

@main def run() = {
  val button4 = Button(variant = Variant.Secondary, icon = _.Home)
  println(button4)
}
