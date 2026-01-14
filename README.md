# Pokladní systém - Dokumentace

## Úvodem
Tento dokument popisuje principy práce se soubory a návrh vícevrstvé aplikace na příkladu pokladního systému POS (Point of sale).

## Popis

Pokladní systém je desktopová aplikace v Javě s využitím Swing pro GUI. Aplikace umožňuje:

- Správu produktů a jejich prodeje
- Generování účtenek
- Práci s databází produktů (SVG)
- Uživatelské rozhraní pro pokladní operace

Projekt je koncipován jako vícevrstvá aplikace.

## Principy práce se soubory v Javě

Práce se soubory je fundamentální částí většiny aplikací. Java poskytuje několik přístupů k práci se soubory, každý s vlastními výhodami a nevýhodami.

### Základní koncepty

1. **Streamy**: Java pracuje se soubory prostřednictvím vstupních a výstupních streamů
2. **Textové vs. binární soubory**: Rozdíl v zpracování textových a binárních dat
3. **Bufferování**: Optimalizace I/O operací pomocí bufferování
4. **Zpracování výjimek**: Důležité pro robustní práci se soubory

### Hlavní přístupy k práci se soubory

#### 1. FileReader/FileWriter

Základní třídy pro práci s textovými soubory:

```java
FileReader reader = new FileReader("soubor.txt");
int character;
while ((character = reader.read()) != -1) {
    // Zpracování znaku
}
reader.close();
```

**Výhody**:
- Jednoduché API
- Vhodné pro malé soubory

**Nevýhody**:
- Není optimalizováno pro velké soubory
- Žádné bufferování
- Nutnost ručního zavírání zdrojů

#### 2. BufferedReader/BufferedWriter

Bufferované třídy pro efektivnější práci:

```java
BufferedReader br = new BufferedReader(new FileReader("soubor.txt"));
String line;
while ((line = br.readLine()) != null) {
    // Zpracování řádku
}
br.close();
```

**Výhody**:
- Bufferování zlepšuje výkon
- Metody pro čtení celých řádků
- Efektivnější pro větší soubory

**Nevýhody**:
- Stále nutnost ručního zavírání
- Pouze pro textové soubory

#### 3. FileInputStream/FileOutputStream

Pro práci s binárními daty:

```java
FileInputStream fis = new FileInputStream("soubor.bin");
int byteData;
while ((byteData = fis.read()) != -1) {
    // Zpracování bajtu
}
fis.close();
```

**Výhody**:
- Práce s binárními daty
- Flexibilita

**Nevýhody**:
- Nízká úroveň abstraktu
- Nutnost ručního zpracování

#### 4. Java NIO (New I/O)

Moderní přístup s lepsí výkonností:

```java
Path path = Paths.get("soubor.txt");
List<String> lines = Files.readAllLines(path);
```

**Výhody**:
- Lepší výkon
- Jednodušší API
- Podpora pro velké soubory
- Automatické zavírání zdrojů

**Nevýhody**:
- Vyžaduje Java 7+
- Menší kontrola nad detailními operacemi

#### 5. Scanner

Pro parsování strukturovaných dat:

```java
Scanner scanner = new Scanner(new File("data.txt"));
while (scanner.hasNext()) {
    String token = scanner.next();
    // Zpracování
}
scanner.close();
```

**Výhody**:
- Snadné parsování
- Podpora regulárních výrazů

**Nevýhody**:
- Pomalejší pro velké soubory
- Omezené možnosti formátování

### Try-with-resources

Moderní přístup k správě zdrojů (Java 7+):

```java
try (BufferedReader br = new BufferedReader(new FileReader("soubor.txt"))) {
    String line;
    while ((line = br.readLine()) != null) {
        // Zpracování
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

Tento přístup zajišťuje automatické zavření zdrojů, což zvyšuje robustnost kódu.

## Vícevrstvá architektura

Vícevrstvá architektura je návrhový vzor, který rozděluje aplikaci do logických vrstev, každá s vlastní zodpovědností. Tento přístup zlepšuje udržovatelnost, testovatelnost a flexibilitu aplikace.

### Základní vrstvy

#### 1. Prezentační vrstva

Zodpovědná za interakci s uživatelem:

- Grafické uživatelské rozhraní (GUI)
- Zpracování uživatelského vstupu
- Zobrazení dat
- Validace vstupu

**Výhody**:
- Oddělení uživatelského rozhraní od obchodní logiky
- Možnost změny UI bez dopadu na zbytek aplikace
- Podpora pro různé typy rozhraní (desktop, web, mobil)

**Nevýhody**:
- Dodatečná složitost
- Nutnost synchronizace mezi vrstvami

#### 2. Obchodní logika (Business Logic Layer)

Jádro aplikace obsahující pravidla a procesy:

- Validace obchodních pravidel
- Výpočty a transformace dat
- Koordinace mezi datovou vrstvou a prezentací
- Implementace specifických algoritmů

**Výhody**:
- Centralizace obchodní logiky
- Snadnější údržba a testování
- Možnost opětovného použití

**Nevýhody**:
- Možné problémy s výkonem při špatném návrhu
- Složitost při komplexních obchodních pravidlech

#### 3. Datová vrstva (Data Access Layer)

Zodpovědná za přístup a manipulaci s daty:

- Práce se soubory
- Databázové operace
- Cache a optimalizace přístupu
- Transformace datových formátů

**Výhody**:
- Abstrakce zdroje dat
- Možnost změny úložiště bez dopadu na zbytek
- Centralizace přístupu k datům

**Nevýhody**:
- Dodatečná vrstva může ovlivnit výkon
- Složitost při komplexních dotazech

#### 4. Přenosové objekty (DTO - Data Transfer Objects)

Objekty pro přenos dat mezi vrstvami:

- Jednoduché datové struktury
- Bez obchodní logiky
- Pouze pro přenos dat

**Výhody**:
- Snížení závislostí mezi vrstvami
- Zlepšení výkonu
- Snadná serializace

**Nevýhody**:
- Dodatečný kód pro mapování
- Nutnost udržovat synchronizaci

### Návrhové vzory pro vícevrstvé aplikace

#### 1. MVC (Model-View-Controller)

- **Model**: Datová struktura a obchodní logika
- **View**: Prezentace dat
- **Controller**: Zprostředkovatel mezi modelem a view

#### 2. Repository Pattern

Abstraktní vrstva pro přístup k datům:

```java
public interface ProductRepository {
    List<Product> findAll();
    Product findById(String id);
    void save(Product product);
}
```

#### 3. Service Layer Pattern

Vrstva služeb pro koordinaci operací:

```java
public class OrderService {
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    
    public void createOrder(Order order) {
        // Koordinace mezi repository
    }
}
```

#### 4. Dependency Injection

Vkládání závislostí pro lepší testovatelnost:

```java
public class OrderService {
    private final ProductRepository productRepository;
    
    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
```

### Výhody vícevrstvé architektury

1. **Oddělení zodpovědností**: Každá vrstva má jasně definovanou roli
2. **Udržovatelnost**: Snadnější úpravy a rozšíření
3. **Testovatelnost**: Možnost testovat vrstvy nezávisle
4. **Flexibilita**: Možnost změny implementace jedné vrstvy bez dopadu na ostatní
5. **Znovupoužitelnost**: Komponenty lze použít v jiných projektech
6. **Skalovatelnost**: Snadnější škálování jednotlivých částí

### Nevýhody vícevrstvé architektury

1. **Složitost**: Vyšší počáteční náklady na návrh
2. **Výkon**: Dodatečné vrstvy mohou ovlivnit výkon
3. **Nadbytečnost**: Pro malé projekty může být zbytečné
4. **Synchronizace**: Nutnost udržovat konzistenci mezi vrstvami

## Implementace v tomto projektu

### Architektura projektu

Projekt používá třívrstvou architekturu:

1. **Prezentační vrstva**: `pokladna.views.GUI`
   - Grafické rozhraní pomocí Swing
   - Zpracování uživatelských akcí
   - Zobrazení produktů a objednávek

2. **Obchodní logika**: `pokladna.logic.orderLogic`
   - Správa objednávek
   - Výpočty celkových cen
   - Validace obchodních pravidel

3. **Datová vrstva**: `pokladna.data.productData`
   - Implementace rozhraní `ProductDataSource`
   - Práce s CSV soubory
   - Cache produktů v paměti

### Implementace práce se soubory

Projekt používá CSV formát pro ukládání produktů. Implementace v `productData.java`:

```java
public List<Product> loadProducts() throws Exception {
    products.clear();
    productById.clear();
    productByEan.clear();

    File file = new File(PRODUCTS_FILE);
    if (!file.exists()) {
        throw new FileNotFoundException("File not found: " + PRODUCTS_FILE);
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        boolean firstLine = true;
        
        while ((line = br.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue; // Skip header
            }
            
            String[] parts = line.split(";");
            if (parts.length >= 4) {
                String id = parts[0].trim();
                String ean = parts[1].trim();
                String name = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());
                
                Product product = new Product(id, ean, name, price);
                products.add(product);
                productById.put(id, product);
                productByEan.put(ean, product);
            }
        }
    }
    
    return products;
}
```

Tato implementace používá:
- `BufferedReader` pro efektivní čtení souboru
- `try-with-resources` pro automatické zavírání zdrojů
- Parsování CSV formátu s oddělovačem ";"
- Validaci struktury dat
- Cache produktů v paměti pomocí `HashMap`

### Výhody zvolené implementace

1. **Efektivita**: Bufferované čtení zlepšuje výkon
2. **Robustnost**: Automatické zavírání zdrojů
3. **Cache**: Rychlý přístup k produktům přes HashMap
4. **Validace**: Kontrola struktury dat
5. **Oddělení**: Jasné rozhraní `ProductDataSource`

### Možná vylepšení

1. **Použití Java NIO**: Modernější přístup s lepším výkonem
2. **Podpora pro více formátů**: JSON, XML, databáze
3. **Asynchronní načítání**: Pro lepší uživatelský zážitek
4. **Validace dat**: Komplexnější kontrola vstupních dat
5. **Logging**: Lepší sledování chyb

## Porovnání různých přístupů


### Textové vs. Binární formáty

**Textové (CSV, JSON, XML)**:
- Výhody: Čitelnost, snadná editace, kompatibilita
- Nevýhody: Vyšší spotřeba místa, pomalejší parsování

**Binární**:
- Výhody: Kompaktní, rychlé čtení/zápis
- Nevýhody: Nečitelné, složitější debugování

### Synchronní vs. Asynchronní přístup

**Synchronní**:
- Výhody: Jednoduchost, sekvenční zpracování
- Nevýhody: Blokování UI, horší uživatelský zážitek

**Asynchronní**:
- Výhody: Lepší responsivita, paralelní zpracování
- Nevýhody: Složitost, problémy s synchronizací

## Závěr

POS představuje vícevrstvou aplikaci s efektivní implementací práce se soubory.

Zapamatovat si:

1. **Vícevrstvá architektura** zlepšuje udržovatelnost, flexibilitu aplikace a škálovatelnost
2. **Práce se soubory** vyžaduje pečlivé zvážení přístupu s ohledem na výkon a robustnost
3. **CSV formát** je vhodný pro jednoduché aplikace, ale má své limity
4. **Moderní Java konstrukce** jako try-with-resources zvyšují kvalitu kódu
5. **Návrhové vzory** pomáhají vytvářet čistší a lépe udržovatelný kód

## Zdroje

https://java-design-patterns.com/
https://www.geeksforgeeks.org/java/file-handling-in-java/
https://www.w3schools.com/java/java_files.asp
https://www.w3schools.com/java/java_files_create.asp
https://www.w3schools.com/java/java_files_write.asp
https://www.w3schools.com/java/java_files_delete.asp
https://www.geeksforgeeks.org/java/java-io-bufferedreader-class-java/
https://www.geeksforgeeks.org/java/io-bufferedwriter-class-methods-java/
https://www.geeksforgeeks.org/java/java-string-format-method-with-examples/