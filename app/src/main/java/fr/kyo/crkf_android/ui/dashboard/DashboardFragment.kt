package fr.kyo.crkf_android.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.kyo.crkf_android.*
import fr.kyo.crkf_android.databinding.FragmentDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.classificationsButton.setOnClickListener {
            val service = Retrofit.Builder()
                .baseUrl("https://crkf.sackebandt.fr/api/")
                //.baseUrl("http://127.0.0.1:80/api/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ClassificationService::class.java)

            service.getClassifications().enqueue(object : Callback<ClassificationResponse> {

                override fun onResponse(call: Call<ClassificationResponse>, response: Response<ClassificationResponse>) {
                    binding.classificationsTextView.text = response.body()?.results?.get(0)?.classification?: "nothing"
                }

                override fun onFailure(call: Call<ClassificationResponse>, t: Throwable) {
                    Log.d("TAG_", "An error happened!")
                    t.printStackTrace()
                }

            })

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}