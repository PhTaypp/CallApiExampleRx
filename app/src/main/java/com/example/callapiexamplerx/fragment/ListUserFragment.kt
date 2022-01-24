package com.example.callapiexamplerx.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.callapiexamplerx.R
import com.example.callapiexamplerx.adapter.UserAdapter
import com.example.callapiexamplerx.activity.ListRepoActivity
import com.example.callapiexamplerx.api.ApiCoroutines
import com.example.callapiexamplerx.model.Users
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListUserFragment : Fragment() {
    var scope = CoroutineScope(CoroutineName("myScope"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_user, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Khởi tạo RecyclerView.
        val rvUsers = view.findViewById<RecyclerView>(R.id.rv_users_coroutine)
        rvUsers.layoutManager = LinearLayoutManager(activity)
        val adapter = UserAdapter(requireContext())
        rvUsers.adapter = adapter
//        // Khởi tạo OkHttpClient để lấy dữ liệu.
//        val client = OkHttpClient()

//        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
//        val moshi: Moshi = Moshi.Builder().build()
//        val usersType: Type = Types.newParameterizedType(
//            MutableList::class.java,
//            Users::class.java
//        )
//        val jsonAdapter: JsonAdapter<List<Users>> = moshi.adapter(usersType)

        // Tạo request lên server.
        val request = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = request.create(ApiCoroutines.GitHubService::class.java)
        scope.launch(Dispatchers.Main) {
            //loading
            withContext(Dispatchers.Main) {
                (requireActivity() as ListRepoActivity).showLoading()
            }
            delay(5000)
            service.listRepos().enqueue(object : Callback<List<Users>> {
                override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                    Log.d("AHIHI", "ABC")
                    (requireActivity() as ListRepoActivity).hideLoading()
                    adapter.setData(response.body()!!)
                }

                override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                    (requireActivity() as ListRepoActivity).hideLoading()
                }
            })
        }
        adapter.listUserOnItemClickListener = object : UserAdapter.OnClickListener {
            override fun onClickItem(
                u : Users
            ) {
                showAvaUser(u.avatar_url.toString())
            }

        }
        // Thực thi request.
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("Error", "Network Error")
//            }
//
//            @Throws(IOException::class)
//            override fun onResponse(call: Call, response: Response) {
//
//                // Lấy thông tin JSON trả về.
//                val json = response.body()!!.string()
//                val users: List<Users>? = jsonAdapter.fromJson(json)
//                Log.i("response", "json   $json")
//
//                // Cho hiển thị lên RecyclerView.
////                activity?.runOnUiThread {
//                    users?.let {
//                        Log.d("DATA DCM", it.size.toString())
//                        adapter.setData(it)
////                    }
//                }
//
//
//            }
//        })
    }

    private fun showAvaUser(avatarUrl : String){
        val dialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        val contentView: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_image_user, null)
        dialog.setContentView(contentView)
        dialog.setCancelable(false)
        val window: Window? = dialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setBackgroundDrawableResource(R.color.transparent)
        val imgAva = dialog.findViewById(R.id.imgAvatar) as ImageView
        Glide.with(requireContext()).load(avatarUrl).into(imgAva)
        val okBtn = dialog.findViewById(R.id.btnOk) as Button
        okBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}