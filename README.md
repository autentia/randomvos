# randomvos - Random Value Objects
A simple library to construct Value Objects filled with random values.

This is useful for unit testing VOs and their Builders, making sure all properties are properly set and `equals()` and `hashCode()` methods are correctly defined.

## Install
The library is available on Maven Central, so just add the dependency in your `pom.xml`:
```
<dependency>
  <groupId>com.autentia</groupId>
  <artifactId>randomvos</artifactId>
  <version>1.0.0</version>
  <scope>test</scope>
</dependency>
```

## Usage
`ExtendedRandomBuilder` is the entry point.

Configure and build an `ExtendedRandom` and then call any of the object-related methods: `nextObject()`, `nextObjectFromBuilder()` or `nextObjectsFromPrototype()`.

If your VOs use generic types or type hierarchies, most probably you will have to register one or more `RandomizerSelector`s with corresponding `Randomizer` implementations like `EnumRandomizer`, `ListRandomizer` and `MultiTypeRandomizer`.

See the JavaDoc for more details.
