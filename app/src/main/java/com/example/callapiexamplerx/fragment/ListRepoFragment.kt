package com.example.callapiexamplerx.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.example.callapiexamplerx.model.Users
import com.example.callapiexamplerx.activity.ListRepoActivity
import com.example.callapiexamplerx.adapter.RepoAdapter
import com.example.callapiexamplerx.api.ApiCoroutines
import com.example.callapiexamplerx.model.Item
import com.example.callapiexamplerx.model.RepoModel
import com.example.callapiexamplerx.utils.Constant
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.*
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


class ListRepoFragment : Fragment() {
    var scope = CoroutineScope(CoroutineName("myScope"))
    var listRepoOnItemClickListener: RepoAdapter.OnItemRepoClickListener? = null
    private lateinit var thisContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            thisContext = container.context
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Khởi tạo RecyclerView.
        val rvUsers = view.findViewById<RecyclerView>(R.id.rv_users)
        rvUsers.layoutManager = LinearLayoutManager(activity)
//        val adapter = UserAdapter(requireContext())
//        rvUsers.adapter = adapter
//        // Khởi tạo OkHttpClient để lấy dữ liệu.
//        val client = OkHttpClient()
//
//        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
//        val moshi: Moshi = Moshi.Builder().build()
//        val usersType: Type = Types.newParameterizedType(
//            MutableList::class.java,
//            Users::class.java
//        )
//        val jsonAdapter: JsonAdapter<List<Users>> = moshi.adapter(usersType)
//
//        // Tạo request lên server.
//        val request = Retrofit.Builder()
//            .baseUrl(Constant.URL_GITHUB)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val service = request.create(ApiCoroutines.GitHubService::class.java)
//        scope.launch(Dispatchers.IO) {
//            //loading
//            withContext(Dispatchers.Main) {
//                (requireActivity() as ListUserActivity).showLoading()
//            }
//            delay(1000)
//
                // Khởi tạo OkHttpClient để lấy dữ liệu.
        val client = OkHttpClient()
        val repoAdapter = RepoAdapter(requireContext())
        rvUsers.adapter = repoAdapter
        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        val moshi: Moshi = Moshi.Builder().build()
        val usersType: Type = Types.newParameterizedType(
            MutableList::class.java,
            RepoModel::class.java
        )
        val jsonAdapter: JsonAdapter<List<Users>> = moshi.adapter(usersType)

        // Tạo request lên server.
        val request = Retrofit.Builder()
            .baseUrl(Constant.URL_GITHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = request.create(ApiCoroutines.GitHubService::class.java)
        scope.launch(Dispatchers.IO) {
            //loading
            withContext(Dispatchers.Main) {
                (requireActivity() as ListRepoActivity).showLoading()
            }
            delay(1000)
//            service.listRepos().enqueue(object : Callback<List<Users>> {
//                override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
//                    Log.d("AHIHI", "Success")
//                    // hide loading
//                    (requireActivity() as ListUserActivity).hideLoading()
//
//                    adapter.setData(response.body()!!)
//                }
//
//                override fun onFailure(call: Call<List<Users>>, t: Throwable) {
//                    Log.e("Error", "Network Error")
//                    // hide loading
//                    (requireActivity() as ListUserActivity).hideLoading()
//                }
//            })
//        }
//            service.searchRepo("trending","stars").enqueue(object : Callback<List<RepoModel>> {
//                override fun onResponse(call: Call<List<RepoModel>>, response: Response<List<RepoModel>>) {
//                    Log.d("AHIHI", "Success")
//                    // hide loading
//                    (requireActivity() as ListUserActivity).hideLoading()
//
//                    repoAdapter.setListRepo(response.body()!!)
//                }
//
//                override fun onFailure(call: Call<List<RepoModel>>, t: Throwable) {
//                    Log.e("Error", "Network Error")
//                    // hide loading
//                    (requireActivity() as ListUserActivity).hideLoading()
//                }
//            })

//            // Call khong coroutines
//            service.listRepo1("trending","stars").enqueue(object : retrofit2.Callback<RepoModel> {
//
//                override fun onResponse(
//                    call: retrofit2.Call<RepoModel>,
//                    response: retrofit2.Response<RepoModel>
//                ) {
//                    Log.d("AHIHI", "Success")
//                    // hide loading
//                    (requireActivity() as ListRepoActivity).hideLoading()
//                    repoAdapter.setListRepo(response.body()!!)
//                }
//
//                override fun onFailure(call: Call<RepoModel>, t: Throwable) {
//                    Log.e("Error", "Network Error")
//                    // hide loading
//                    (requireActivity() as ListRepoActivity).hideLoading()
//                }
//            })

            // dung coroutines
            scope.launch(Dispatchers.IO) {
                service.listRepo("trending","stars")
                    .let { gitResponse ->
                        scope.launch(Dispatchers.Main) {
                            (requireActivity() as ListRepoActivity).hideLoading()
                            repoAdapter.setListRepo(gitResponse.body()!!)
                        }
                    }
            }

        }
        repoAdapter.listRepoOnItemClickListener = object : RepoAdapter.OnItemRepoClickListener {
            override fun onClickItem(item: Item) {
                showAvaRepo(item.owner.avatarUrl)
            }
        }


//
//        }
//        // Thực thi request.
////        client.newCall(request).enqueue(object : Callback {
////            override fun onFailure(call: Call, e: IOException) {
////                Log.e("Error", "Network Error")
////            }
////
////            @Throws(IOException::class)
////            override fun onResponse(call: Call, response: Response) {
////
////                // Lấy thông tin JSON trả về.
////                val json = response.body()!!.string()
////                val users: List<Users>? = jsonAdapter.fromJson(json)
////                Log.i("response", "json   $json")
////
////                // Cho hiển thị lên RecyclerView.
//////                activity?.runOnUiThread {
////                    users?.let {
////                        Log.d("DATA DCM", it.size.toString())
////                        adapter.setData(it)
//////                    }
////                }
////
////
////            }
////        })
//    }
    }
    private fun showAvaRepo(avatarUrl : String){
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

