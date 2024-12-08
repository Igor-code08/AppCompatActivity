import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.appcompatactivity.R

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Инициализация базы данных
        dbHelper = DatabaseHelper(this)

// Элементы интерфейса
        val etProductName = findViewById<EditText>(R.id.etProductName)
        val etProductWeight = findViewById<EditText>(R.id.etProductWeight)
        val etProductPrice = findViewById<EditText>(R.id.etProductPrice)

        val btnSave = findViewById<Button>(R.id.btnSave)

        val lvProducts = findViewById<ListView>(R.id.lvProducts)

        // Загрузка данных из базы и установка адаптера
        loadProducts(lvProducts)

        // Обработка нажатия кнопки "Сохранить"
        btnSave.setOnClickListener {
            val name = etProductName.text.toString()
            val weight = etProductWeight.text.toString().toDoubleOrNull()
            val price = etProductPrice.text.toString().toDoubleOrNull()

            if (name.isNotEmpty() && weight != null && price != null) {
                dbHelper.addProduct(name, weight, price)
                loadProducts(lvProducts) // Обновляем список продуктов

                // Очищаем поля ввода
                etProductName.text.clear()
                etProductWeight.text.clear()
                etProductPrice.text.clear()
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Загрузка продуктов из базы данных и обновление ListView
    private fun loadProducts(listView: ListView) {
        val products = dbHelper.getAllProducts()
        adapter = ProductAdapter(this, products)
        listView.adapter = adapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finish() // Закрываем приложение
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}