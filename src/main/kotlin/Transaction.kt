import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class Transaction(

    val uuid: Long,
    val description: String,
    val category: String,
    val operation: String,
    val amount: Float,
    val status: String,
    @SerializedName("creation_date") val creationDate: String

){
    fun getCreationDate(): Calendar{

        val pattern = "dd/MM/yyyy"
        val date = SimpleDateFormat(pattern).parse(creationDate)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar

    }

}