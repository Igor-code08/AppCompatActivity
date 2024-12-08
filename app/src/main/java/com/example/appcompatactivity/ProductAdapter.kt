import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ProductAdapter(private val context: Context, private val products: List<Product>) : BaseAdapter() {

    override fun getCount(): Int = products.size

    override fun getItem(position: Int): Any = products[position]

    override fun getItemId(position: Int): Long = products[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

        val product = products[position]

        // Заполняем данные о продукте
        view.findViewById<TextView>(android.R.id.text1).text = product.name
        view.findViewById<TextView>(android.R.id.text2).text = "Вес: ${product.weight} г | Цена: ${product.price} руб."

        return view
    }
}