package fr.kyo.crkf_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.moshi.Json
import fr.kyo.crkf_android.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}

data class ClassificationResponse(val results: List<Classification>)
/*
data class Classification(@field:Json(name = "idClassification") val id: Int,
                          @field:Json(name = "classification") val classification: String)

*/
data class Classification(val idClassification: Int, val classification: String)
interface ClassificationService {
    @GET("/classifications")
    fun getClassifications(): Call<ClassificationResponse>
}


/*
data class WordResponse(val results: List<Word>)
data class Word(val word: String)
interface WordService {
    @GET("/word")
    fun getWord(): Call<WordResponse>
}


data class UserResponse(val results: List<User>)
data class User(val email: String, val phone: String)
interface UserService {
    @GET("/api")
    fun getUsers(): Call<UserResponse>
}

 */