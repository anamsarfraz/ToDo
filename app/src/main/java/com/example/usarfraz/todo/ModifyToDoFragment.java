package com.example.usarfraz.todo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModifyToDoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModifyToDoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifyToDoFragment extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TODO = "todo";

    private Todo todo;

    private OnFragmentInteractionListener mListener;

    public ModifyToDoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param todo Todo.
     * @return A new instance of fragment ModifyToDoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModifyToDoFragment newInstance(Todo todo) {
        ModifyToDoFragment fragment = new ModifyToDoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TODO, todo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            todo = (Todo) getArguments().getSerializable(ARG_TODO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modify_todo, container);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d("Fragment", "Menu Inflate");
        inflater.inflate(R.menu.menu_modify, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:

                // Return input item to activity through the implemented listener
                mListener = (OnFragmentInteractionListener) getActivity();
                mListener.onFragmentInteraction(todo);
                // Close the dialog and return back to the parent activity
                dismiss();
                return true;
            case R.id.item_cancel:
                // Close the dialog and return back to the parent activity
                dismiss();
                return true;

        }
        dismiss();
        return super.onOptionsItemSelected(item);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Todo todo) {
        if (mListener != null) {
            mListener.onFragmentInteraction(todo);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Todo todo);
    }
}
