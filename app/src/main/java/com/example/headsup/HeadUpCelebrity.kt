package com.example.headsup

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.headsup.databinding.ActivityHeadUpCelebrityBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Response

class HeadUpCelebrity: AppCompatActivity() {
    lateinit var showData: RecyclerView
    lateinit var Binding: ActivityHeadUpCelebrityBinding
    val listsCel = ArrayList<UsersItem>()
    val listsCelPk = ArrayList<UserItemPk>()

    var holdPk = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Binding = ActivityHeadUpCelebrityBinding.inflate(layoutInflater)

        setContentView(Binding.root)

        showData = findViewById(R.id.rvMain)
        showData.layoutManager = LinearLayoutManager(this)

        getData()
        postDate()
        search()
    }

    fun getData() {
        val data = APIClient().getClient()?.create(APIInterface::class.java)
        val getHead = data?.getdata()

        if (getHead != null) {
            getHead.enqueue(object : retrofit2.Callback<ArrayList<UsersItem>?> {

                override fun onResponse(
                    call: retrofit2.Call<ArrayList<UsersItem>?>,
                    response: Response<ArrayList<UsersItem>?>
                ) {
                    if (response.code() == 200) {
                        for (resp in response.body()!!) {
                            listsCel.add(resp)
                            showData.adapter = RVAdapter(listsCel)
                            showData.adapter!!.notifyDataSetChanged()
                        }
                    } else {
                        Log.d("response code ", "${response.code()}")
                    }
                }

                override fun onFailure(call: retrofit2.Call<ArrayList<UsersItem>?>, t: Throwable) {
                    Log.d("response code ", "${t.message}")
                }
            })
        }
    }

    fun postDate() {

        Binding.btnAdd.setOnClickListener {
            alertDialog()
        }
    }

    fun alertDialog() {

        var postDate = APIClient().getClient()?.create(APIInterface::class.java)

        var newCel = UsersItem("", "", "", "")

        var buildDialog: AlertDialog.Builder = AlertDialog.Builder(this@HeadUpCelebrity)

        var viewShow = layoutInflater.inflate(R.layout.activity_new_celebrity, null)

        var nameCel = viewShow.findViewById<TextInputEditText>(R.id.nameCel).text
        var taboo1 = viewShow.findViewById<TextInputEditText>(R.id.taboo1Cel).text
        var taboo2 = viewShow.findViewById<TextInputEditText>(R.id.taboo2Cel).text
        var taboo3 = viewShow.findViewById<TextInputEditText>(R.id.taboo3Cel).text

        buildDialog.setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->

            newCel = UsersItem(
                nameCel.toString(),
                taboo1.toString(),
                taboo2.toString(),
                taboo3.toString()
            )

            var postHeadUp = postDate?.postdata(newCel)
            if (postHeadUp != null) {
                postHeadUp.enqueue(object : retrofit2.Callback<ArrayList<UsersItem>?> {
                    override fun onResponse(
                        call: retrofit2.Call<ArrayList<UsersItem>?>,
                        response: Response<ArrayList<UsersItem>?>
                    ) {
                        if (response.code() == 200) {
                            Log.d("response", "${response.isSuccessful()}")
                            showData.adapter!!.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<ArrayList<UsersItem>?>,
                        t: Throwable
                    ) {
                        Log.d("Post error", "${t.message}")
                    }


                })
            }
        })
        buildDialog.setView(viewShow)
        buildDialog.create()
        buildDialog.show()

    }

    fun getDataPk(submitName: String) {
        Log.d("Hold Name", "$submitName")

        var getDate = APIClient().getClient()?.create(APIInterface::class.java)
        var test = getDate?.getdataWithPk2()

        if (test != null) {
            test.enqueue(object : retrofit2.Callback<ArrayList<UserItemPk>?> {
                override fun onResponse(
                    call: retrofit2.Call<ArrayList<UserItemPk>?>,
                    response: Response<ArrayList<UserItemPk>?>
                ) {
                    if (response.code() == 200) {
                        for (resp in response.body()!!) {
                            listsCelPk.add(resp)
                        }
                    }
                }

                override fun onFailure(call: retrofit2.Call<ArrayList<UserItemPk>?>, t: Throwable) {
                    Log.d("PK", "${t.message}")
                }


            })
        }
        for (test1 in listsCelPk)
            if (submitName == test1.name) {
                Log.d("43", "${test1.name}")

                Log.d("43", "${listsCelPk.indexOf(test1)}")

                holdPk = listsCelPk.indexOf(test1)
                var holdID = listsCelPk[holdPk].pk
                var holdName = listsCelPk[holdPk].name

                Log.d("holdNameList", "${holdID}")
                Log.d("holdNameList", "${holdName}")

                getSpecified(holdID, holdName, holdPk)
            }
    }

    fun getSpecified(PK: Int, name: String, holdHintPk: Int) {

        holdPk = holdHintPk

        var getData = APIClient().getClient()?.create(APIInterface::class.java)

        var getDatePk = getData?.getspicifdata(PK, name)

        if (getDatePk != null) {

            getDatePk.enqueue(object : retrofit2.Callback<ArrayList<UserItemPk>> {
                override fun onResponse(
                    call: Call<ArrayList<UserItemPk>>,
                    response: Response<ArrayList<UserItemPk>>
                ) {
                    if (response.code() == 200) {

                    } else {
                        Log.d("6547", "${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserItemPk>>, t: Throwable) {
                    Log.d("API", "${t.message}")
                }
            })

        }

        getDataAPI(holdPk)


    }

    fun search() {

        var holdName = Binding.etCelName.text

        Binding.btnSubmit.setOnClickListener {
            Log.d("Hold Name", "${holdName.toString()}")
            println(holdName)

            getDataPk(holdName.toString())
        }
    }

    fun getDataAPI(receivePK: Int) {

        Log.d("PK", "${receivePK}")

        var editData = APIClient().getClient()?.create(APIInterface::class.java)

        var editCel = UsersItem("", "", "", "")

        val buildDialog: AlertDialog.Builder = AlertDialog.Builder(this@HeadUpCelebrity)


        var viewShow = layoutInflater.inflate(R.layout.activity_put_celebrity, null)

        var nameCel = viewShow.findViewById<TextInputEditText>(R.id.namePutCel)
        var taboo1 = viewShow.findViewById<TextInputEditText>(R.id.taboo1PutCel)
        var taboo2 = viewShow.findViewById<TextInputEditText>(R.id.taboo2PutCel)
        var taboo3 = viewShow.findViewById<TextInputEditText>(R.id.taboo3PutCel)


        var nameTextHint = viewShow.findViewById<TextInputLayout>(R.id.namePutCel)
        var taboo1TextHint = viewShow.findViewById<TextInputLayout>(R.id.taboo1PutCel)
        var taboo2TextHint = viewShow.findViewById<TextInputLayout>(R.id.taboo2PutCel)
        var taboo3TextHint = viewShow.findViewById<TextInputLayout>(R.id.taboo3PutCel)




        nameTextHint.hint = listsCelPk[receivePK].name
        taboo1TextHint.hint = listsCelPk[receivePK].taboo1
        taboo2TextHint.hint = listsCelPk[receivePK].taboo2
        taboo3TextHint.hint = listsCelPk[receivePK].taboo3


        var name = nameCel.text
        var taboo1Cel = taboo1.text
        var taboo2Cel = taboo2.text
        var taboo3Cel = taboo3.text

        buildDialog.setPositiveButton("Edit", DialogInterface.OnClickListener { dialog, which ->
            editCel =
                UsersItem(name.toString(), taboo1.toString(), taboo2.toString(), taboo3.toString())

            var sendEditData = editData?.updatedata(listsCelPk[receivePK].pk, editCel)

            if (sendEditData != null) {
                sendEditData.enqueue(object : retrofit2.Callback<ArrayList<UsersItem>?> {
                    override fun onResponse(
                        call: Call<ArrayList<UsersItem>?>,
                        response: Response<ArrayList<UsersItem>?>
                    ) {
                        if (response.code() == 200) {
                            Log.d("Seuss", "${response.isSuccessful}")
                            showData.adapter!!.notifyDataSetChanged()
                        } else {
                            Log.d("coders", "${response.code()}")

                        }
                    }

                    override fun onFailure(call: Call<ArrayList<UsersItem>?>, t: Throwable) {
                        Log.d("Fail", "${t.message}")
                    }

                })
            }
        })

        buildDialog.setNeutralButton("Delete", DialogInterface.OnClickListener { dialog, which ->

            var deleteData = editData?.deletedata(listsCelPk[receivePK].pk)

            if (deleteData != null) {
                deleteData.enqueue(object : retrofit2.Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.code() == 200) {
                            Log.d("success", "${response.isSuccessful}")
                            showData.adapter!!.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("FailDelete ", "${t.message}")
                    }
                })
            }
        })

        buildDialog.setView(viewShow)
        buildDialog.create()
        buildDialog.show()


    }

}