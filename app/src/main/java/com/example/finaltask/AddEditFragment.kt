package com.example.finaltask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

private const val TAG = "AddEditFragment.kt"

class AddEditFragment(private val dataAdapter: DataAdapter) : Fragment() {

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


        when (requireArguments().getString(ACTION_KEY)) {

            ACTION_ADD -> {
                btAddEditFragmentConfirm.text = resources.getString(R.string.btAddFragmentText)
                btAddEditFragmentConfirm.setOnClickListener {

                    val name = etAddEditFragmentName.text.toString()
                    if (DataModel.nameAlreadyExists(name)) {

                        Toast.makeText(
                            context,
                            resources.getString(R.string.toastNameAlreadyExists),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {

                        val url = etAddEditFragmentUrl.text.toString()

                        DbHelper(requireContext(), null).addName(name, url)

                        Toast.makeText(
                            context,
                            "$name ${resources.getString(R.string.toastAdded)}",
                            Toast.LENGTH_LONG
                        ).show()

                        dataAdapter.refreshDataList(requireContext())
                        finish()
                    }
                }
            }

            ACTION_EDIT -> {
                btAddEditFragmentConfirm.text = resources.getString(R.string.btEditFragmentText)

                val row: Int

                with(requireArguments()) {
                    row = getString(DbHelper.ROW_COL)!!.toInt()
                    etAddEditFragmentName.setText(getString(DbHelper.NAME_COL)!!)
                    etAddEditFragmentUrl.setText(getString(DbHelper.URL_COL)!!)
                }

                btAddEditFragmentConfirm.setOnClickListener {

                    val name = etAddEditFragmentName.text.toString()
                    val url = etAddEditFragmentUrl.text.toString()

                    DbHelper(requireContext(), null).editName(row, name, url)

                    Toast.makeText(
                        context,
                        "$name ${resources.getString(R.string.toastSaved)}",
                        Toast.LENGTH_LONG
                    ).show()

                    dataAdapter.refreshDataList(requireContext())
                    finish()
                }
            }
        }

        btAddEditFragmentCancel.setOnClickListener {
            finish()
        }

        return view
    }

    override fun onDestroyView() {
        Log.d(TAG,"onDestroyView called")
        super.onDestroyView()
    }

    private fun finish() {
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    companion object {

        const val ACTION_KEY = "action"
        const val ACTION_ADD = "add"
        const val ACTION_EDIT = "edit"
    }
}