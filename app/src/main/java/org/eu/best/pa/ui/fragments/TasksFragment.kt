package org.eu.best.pa.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import androidx.fragment.app.Fragment

import org.eu.best.pa.R
import org.eu.best.pa.ui.MainActivity

class TasksFragment : Fragment() {

    private var btnMenu: ImageButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnMenu = view.findViewById(R.id.btn_menu)

        btnMenu?.setImageResource(R.drawable.ic_menu)

        btnMenu?.setOnClickListener {
            val parentActivity = requireActivity()

            if (parentActivity is MainActivity) {
                parentActivity.openDrawer()
            }
        }
    }
}