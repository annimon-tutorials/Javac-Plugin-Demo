# Javac Plugin Demo

## CharStat

Calculates number of characters of the compiled source code.

## MethodsCount

Calculates methods count per source file and outputs overall methods count.

## MapAccess

Allows to access the `Map` with string literal key as the array:

```java
Map<String, String> map = new HashMap<>();
map["key"] = "ten"; // map.put("key", "ten")
System.out.println(map["key"]); // map.get("key")
```

## OperatorOverloading

Overloads `+ - * /` operators to map to `add subtract multiply divide` methods.

```java
BigInteger x1 = BigInteger.TEN;
BigInteger x2 = BigInteger.valueOf(120);
BigInteger x3 = x1 + x2;
// BigInteger x3 = x1.add(x2);
BigInteger x4 = BigInteger.valueOf(2) * x3;
// BigInteger x4 = BigInteger.valueOf(2).multiply(x3);
BigInteger x5 = x4 - x2 / x1 + x3;
// BigInteger x5 = x4.subtract(x2.divide(x1)).add(x3);
```

## ExtensionMethods

Allows to use extension methods. The extension method should be declared as `public static` in the same class.

```java
public static IntStream filterNot(IntStream stream, IntPredicate predicate) {
    return stream.filter(predicate.negate());
}

public static <T, R extends Comparable<? super R>> Stream<T> sortBy(Stream<T> stream, Function<? super T, ? extends R> f) {
    return stream.sorted((o1, o2) -> f.apply(o1).compareTo(f.apply(o2)));
}

apps.stream()
        .filterNot(Application::isInstalled)
        .sortBy(Application::getDownloadingTime())
```
