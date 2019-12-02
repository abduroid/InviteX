package com.abduqodirov.invitex.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.viewmodel.ListViewModelFactory
import com.abduqodirov.invitex.adapters.AytilganClickListener
import com.abduqodirov.invitex.adapters.SingleListRecycleViewAdapter
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.databinding.FragmentSingleListBinding
import com.abduqodirov.invitex.firestore.CloudFirestoreRepo
import com.abduqodirov.invitex.viewmodel.SingleListViewModel

private const val ARG_OBJECT = "object"

class SingleListFragment : Fragment() {

    private var toifa: String = ""
    private lateinit var binding: FragmentSingleListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_single_list,
            container, false
        )

        val application = requireNotNull(activity!!.application)

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        val viewModelFactory = ListViewModelFactory(
            dataSource = dataSource,
            application = application
        )

        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(
                SingleListViewModel::class.java
            )



        binding.viewModel = viewModel

        val mAdapter =
            SingleListRecycleViewAdapter(AytilganClickListener { mehmon ->
                Log.i("checkbox", "Fragment, adapter listener $mehmon")
                viewModel.onMehmonChecked(mehmon)
            })

        binding.mainList.apply {
            adapter = mAdapter
            setHasFixedSize(true)
        }

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            toifa = getString(ARG_OBJECT)
        }

        val mezbon = listOf(Mehmon(ism = CloudFirestoreRepo.username, toifa = "mezbon"))

        viewModel.localSpecificMehmons(toifa)

        viewModel.observeOfItsGuests(toifa)


        viewModel.loadMembers()

        viewModel.memberlar.observe(this, Observer {
            for (member in it) {
                viewModel.loadFirestoreMehmons(toifa = toifa, username = member)
            }
        })


        viewModel.localGuests
            .observe(this@SingleListFragment, Observer { localGuests ->
                mAdapter.submitList(mezbon + localGuests)

                viewModel.toifaniBarchaMehmonlari
                    .observe(this@SingleListFragment, Observer { remoteGuests ->

                        val firestoreMehmonlar = ArrayList<Mehmon>()

                        for (guests in remoteGuests) {
                            firestoreMehmonlar.addAll(guests)
                        }

                        //TODO there is a issue. When list updates, it changes its position in array.

                        mAdapter.submitList(mezbon + localGuests + firestoreMehmonlar)
                    })
            })




        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()
//        Hides keyboard if previously opened page had keyboard
        hideKeyboard(view)

    }

    private fun hideKeyboard(view: View?) {
        if (view != null) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

