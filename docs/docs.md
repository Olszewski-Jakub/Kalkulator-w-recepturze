## Database

``` mermaid
graph LR
  A[Start] --> id1[(Firebase)];
  id1[(Firebase)] --> id2[Remote Config];
  id2[Remote Config] --> B{Data fetched};
  B --> |Yes| C[Convert data];
  B --> |No| E[End];
  C --> D[Insert data to local database];
  D --> E[End];    
```

Function converts JSON object to Map<String, *>. Mainly used for converting fatched data and saving
it in local database.

``` kotlin title="ImportData.kt"
 fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
        when (val value = this[it]) {
            is JSONArray -> {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                JSONObject(map).toMap().values.toList()
            }

            is JSONObject -> value.toMap()
            JSONObject.NULL -> null
            else -> value
        }
    }

```

### Models

``` kotlin title="VitAModel.kt"
data class VitAModel(
    var id: Int = 0,
    var company: String = "",
    var density: Double = 0.0,
    var drops: Int = 0,
    var mass_units: Double = 0.0
)

```

``` kotlin title="VitEModel.kt"
data class VitEModel(
    var id: Int = 0,
    var company: String = "",
    var density: Double = 0.0,
    var drops: Int = 0,
    var mass_units: Double = 0.0
)

```

``` kotlin title="VitAD3Model.kt"
data class VitAD3Model(
    var id: Int = 0,
    var density: Double = 0.0,
    var drops: Int = 0,
)
```

``` kotlin title="OlejkiModel.kt"
data class OlejkiModel(
    var id: Int = 0,
    var type: String = "",
    var density: Double = 0.0,
    var drops: Double = 0.0,
)

```

``` kotlin title="OlejeModel.kt"
data class OlejeModel(
    var id: Int = 0,
    var type: String = "",
    var density: Double = 0.0,
)
```

```kotlin title="AlcoholDegreeModel.kt"
data class AlcoholDegreeModel(
    var id: Int = 0,
    var alcohol_degree: String = "",
    var alcohol_volume_degree: Double = 0.0
)

```

```kotlin title="AlcoholConcentrationModel.kt"
data class AlcoholConcentrationModel(
    var id: Int,
    var alcohol_concentration: String = "",
    var alcohol_volume: Double = 0.0
)
```

## Vitamin A

### Calculations

``` kotlin title="VitACalculations.kt"
    ...
    fun calculate(): Map<String, VitaminAGridModel> {
        //Selecting company (user input)
        when (company) {
            0 -> return Hasco()
            1 -> return Medana()
            2 -> return Fagron()
            else -> {
                Log.d(TAG, "Company Id outside of range. Couldn't perform calculations")
            }
        }
        //Return error Map of VitAGridModels to be used in the grid
        return mapOf(
            "Vit1" to VitaminAGridModel(
                main_vit = "Error",
                main_vit2 = "occurred",
                mass = "Null",
                volume = "Null",
                drops = "Null",
                massunits = "Null",
                howMuchTosell = "Null"
            )
        )
    }
```

To run calculations user has to select `company`, `unit`, `amount` and pass `VitAList`. Each company
has its own calculations

| id | Company | | id | Unit |     
| :--:| :-------:| --- | :--: | :---------: |    
| `0` | Hasco | | `0`  | grams |
| `1` | Medana | | `1`  | mass units |
| `2` | Fragron | | `2`  | milliliters |

```kotlin 
val Vitamins: Map<String, VitaminAGridModel> = VitaminACalculations(
    company = 0, 
    units = 1,
    amount = 1.0,
    vitAList = vitAList
).calculate()
```

## Vitamin E

## Vitamin A+D3

