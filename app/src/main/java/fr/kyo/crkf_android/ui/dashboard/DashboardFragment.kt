package fr.kyo.crkf_android.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.kyo.crkf_android.UserResponse
import fr.kyo.crkf_android.UserService
import fr.kyo.crkf_android.databinding.FragmentDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.classificationsButton.setOnClickListener {
            /* Creates an instance of the UserService using a simple Retrofit builder using Moshi
    * as a JSON converter, this will append the endpoints set on the UserService interface
    * (for example '/api', '/api?results=2') with the base URL set here, resulting on the
    * full URL that will be called: 'https://randomuser.me/api' */
            val service = Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)

            /* Calls the endpoint set on getUsers (/api) from UserService using enqueue method
             * that creates a new worker thread to make the HTTP call */
            service.getUsers().enqueue(object : Callback<UserResponse> {
                /* The HTTP call was successful, we should still check status code and response body
                 * on a production app. This method is run on the main thread */
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    /* This will print the response of the network call to the Logcat */
                    binding.classificationsTextView.text = response.body().toString()
                    //Log.d("TAG_", response.body().toString())
                }

                /* The HTTP call failed. This method is run on the main thread */
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
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