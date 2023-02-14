package fr.kyo.crkf_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import fr.kyo.crkf_android.databinding.FragmentDashboardBinding
import okhttp3.*
import java.io.IOException


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val client = OkHttpClient()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        _binding!!.classificationsButton.setOnClickListener { showClassifications() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showClassifications(){

            val request = Request.Builder()
                .url("https://random-word-api.herokuapp.com/word")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {println("Error!")}
                override fun onResponse(call: Call, response: Response) {
                    response.body()?.let { _binding!!.classificationsTextView.text = it.string() }
                }
            })

    }

}