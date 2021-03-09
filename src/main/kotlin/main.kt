import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main(args: Array<String>) {

    println("***********************************************************************")
    println("Reporte de transacciones")
    println("***********************************************************************")
    println("Generando reporte\n")

    val transactionList = getTransactionList()
    val transactionsByMonth = getTransactionsByMonth(transactionList)

    transactionsByMonth.forEach { (k, transactions) ->
        var pendingTransactions = 0
        var rejectedTransactions = 0

        var income = 0f
        var expense = 0f

        var outTotal = 0f

        val mapCategories   = HashMap<String,Float>()

        transactions.forEach {
            when(it.status){
                "pending" -> pendingTransactions += 1
                "rejected" -> rejectedTransactions += 1
            }

            when{
                (it.status == "done" && it.operation == "in") -> income += it.amount
                (it.status == "done" && it.operation == "out") -> expense += it.amount
            }

            if(it.operation == "out"){
                outTotal += it.amount
                if(mapCategories[it.category] == null){
                    mapCategories[it.category] = 0f
                }
                mapCategories[it.category] = mapCategories[it.category]!! + it.amount
            }

        }

        println(k)
        println("   $pendingTransactions transacciones pendientes")
        println("   $rejectedTransactions bloqueadas\n")
        println(String.format("   $%.2f ingresos\n", income ))
        println(String.format("   $%.2f gastos\n", expense))

        mapCategories.forEach{ (k, v) ->
            println(String.format("       %-17s %%%.1f", k, ((v * 100) / outTotal)))
        }
        println("\n")
    }

}

private fun getTransactionList(): List<Transaction> {
    val gson = Gson()
    val type = object : TypeToken<List<Transaction>>() {}.type

    return gson.fromJson(FileReader("src/main/resources/transactions.json"), type)
}

private fun getTransactionsByMonth(transactions: List<Transaction>): Map<String, ArrayList<Transaction>>{

    val map: LinkedHashMap<String, ArrayList<Transaction>> = LinkedHashMap()
    map["Enero"] = ArrayList()
    map["Febrero"] = ArrayList()
    map["Marzo"] = ArrayList()
    map["Abril"] = ArrayList()
    map["Mayo"] = ArrayList()
    map["Junio"] = ArrayList()
    map["Julio"] = ArrayList()
    map["Agosto"] = ArrayList()
    map["Septiembte"] = ArrayList()
    map["Octubre"] = ArrayList()
    map["Noviembre"] = ArrayList()
    map["Diciembre"] = ArrayList()

    transactions.forEach {
        val calendar = it.getCreationDate()

        when(calendar.get(Calendar.MONTH)){
            0 -> map["Enero"]!!.add(it)
            1 -> map["Febrero"]!!.add(it)
            2 -> map["Marzo"]!!.add(it)
            3 -> map["Abril"]!!.add(it)
            4 -> map["Mayo"]!!.add(it)
            5 -> map["Junio"]!!.add(it)
            6 -> map["Julio"]!!.add(it)
            7 -> map["Agosto"]!!.add(it)
            8 -> map["Septiembte"]!!.add(it)
            9 -> map["Octubre"]!!.add(it)
            10 -> map["Noviembre"]!!.add(it)
            11 -> map["Diciembre"]!!.add(it)
        }

    }

    return map

}