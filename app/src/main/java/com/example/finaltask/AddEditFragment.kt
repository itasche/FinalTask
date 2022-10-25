package com.example.finaltask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.flow.callbackFlow

private const val TAG = "AddEditFragment.kt"

class AddEditFragment : Fragment() {

    private lateinit var etAddEditFragmentName: EditText
    private lateinit var etAddEditFragmentUrl: EditText
    private lateinit var btAddEditFragmentCancel: Button
    private lateinit var btAddEditFragmentConfirm: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) : View {
        Log.d(TAG,"onCreateView called")

        val view: View = inflater.inflate(R.layout.fragment_add_edit, container, false)

        etAddEditFragmentName = view.findViewById(R.id.etAddEditFragmentName)
        etAddEditFragmentUrl = view.findViewById(R.id.etAddEditFragmentUrl)
        btAddEditFragmentCancel = view.findViewById(R.id.btAddEditFragmentCancel)
        btAddEditFragmentConfirm = view.findViewById(R.id.btAddEditFragmentConfirm)

        val bundle = arguments
        val message = bundle!!.getString("mText")

        when (bundle.getString(ACTION_KEY)) {

            ACTION_ADD -> {
                btAddEditFragmentConfirm.text = resources.getString(R.string.btAddText)
                btAddEditFragmentConfirm.setOnClickListener {

                    val db = DbHelper(requireContext(), null)
                    val name = etAddEditFragmentName.text.toString()
                    val url = etAddEditFragmentUrl.text.toString()

                    db.addName(name, url)
                    Toast.makeText(
                        context,
                        "$name${resources.getString(R.string.toastAdded)}",
                        Toast.LENGTH_LONG
                    ).show()


                }
            }

        }

        btAddEditFragmentCancel.setOnClickListener {

        }

        return view
    }

    override fun onDestroyView() {
        Log.d(TAG,"onDestroyView called")
        super.onDestroyView()
    }

    fun finish() {
        val removeFragmentTransaction = parentFragmentManager.beginTransaction()

        addFragmentBundle.putString(AddEditFragment.ACTION_KEY, AddEditFragment.ACTION_ADD)
        addFragment.arguments = addFragmentBundle
        addFragmentTransaction.add(R.id.flWebBookmarks, addFragment).commitNow()
        Log.d(TAG, "Commit done")

//            addFragmentTransaction.remove(addFragment).commitNow()
    }

    companion object {
        const val ACTION_KEY = "action"
        const val ACTION_ADD = "add"
        const val ACTION_EDIT = "edit"
    }
}