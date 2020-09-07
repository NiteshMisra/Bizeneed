package `in`.bizeneed.activity

import `in`.bizeneed.R
import `in`.bizeneed.databinding.ActivitySearchBinding
import `in`.bizeneed.extras.hideKeyBoard
import `in`.bizeneed.fragments.RecentSearches
import `in`.bizeneed.fragments.SearchFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        binding.backBtn.setOnClickListener { onBackPressed() }

        binding.crossBtn.setOnClickListener {
            binding.searchEdt.setText("")
        }

        binding.crossBtn.visibility = View.GONE
        binding.searchEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.crossBtn.visibility = if (binding.searchEdt.text.toString().isEmpty()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }

        })

        binding.searchEdt.setOnEditorActionListener { _, actionId, _ ->
            if (EditorInfo.IME_ACTION_SEARCH == actionId){
                searchFragment()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        addFragment(RecentSearches.newInstance())

    }

    private fun searchFragment() {
        if (binding.searchEdt.text.toString().isEmpty()){
            return
        }
        hideKeyBoard(this)

        addFragment(SearchFragment.newInstance(""))
    }

    private fun addFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.search_container,fragment)
        transaction.commit()

    }
}