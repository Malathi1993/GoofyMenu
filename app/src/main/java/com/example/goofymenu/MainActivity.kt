package com.example.goofymenu

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.goofymenu.model.Options
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private var menuOptions: Array<Options>? = null
    var response =
        "[{\"CatagoryName\":\"Drones\",\"SiteUrl\":\"category-drones\",\"child\":[{\"categoryName\":\"Drones 1\",\"siteUrl\":\"subcategory-drones-1\"},{\"categoryName\":\"Drones 2\",\"siteUrl\":\"subcategory-drones-2\"},{\"categoryName\":\"Fixed Wing Drone\",\"siteUrl\":\"Fixed-Wing-Drone\"}]},{\"CatagoryName\":\"Pre-School\",\"SiteUrl\":\"category-pre-school\",\"child\":[{\"categoryName\":\"Pre-School 1\",\"siteUrl\":\"subcategory-pre-school-1\"},{\"categoryName\":\"Pre-School 2\",\"siteUrl\":\"subcategory-pre-school-2\"}]},{\"CatagoryName\":\"Remote Controlled\",\"SiteUrl\":\"category-remote-controlle\",\"child\":[{\"categoryName\":\"Remote Controlled 1\",\"siteUrl\":\"subcategory-remote-controlled-1\"},{\"categoryName\":\"Remote Controlled 2\",\"siteUrl\":\"subcategory-remote-controlled-2\"}]},{\"CatagoryName\":\"Interactive\",\"SiteUrl\":\"category-interactive\"},{\"CatagoryName\":\"Building Blocks\",\"SiteUrl\":\"category-building-blocks\",\"child\":[{\"categoryName\":\"Building Blocks 1\",\"siteUrl\":\"subcategory-building-blocks-1\"},{\"categoryName\":\"Building Blocks 2\",\"siteUrl\":\"subcategory-building-block-2\"}]},{\"CatagoryName\":\"STEM & SMART\",\"SiteUrl\":\"category-stem-smart\",\"child\":[{\"categoryName\":\"STEM & SMART 1\",\"siteUrl\":\"subcategory-stem-smart-1\"},{\"categoryName\":\"STEM & SMART 2\",\"siteUrl\":\"subcategory-stem-smart-2\"}]},{\"CatagoryName\":\"Collectibles\",\"SiteUrl\":\"category-collectibles\"},{\"CatagoryName\":\"Puzzles & Games\",\"SiteUrl\":\"category-puzzles-games\",\"child\":[{\"categoryName\":\"Puzzles & Games 1\",\"siteUrl\":\"subcategory-puzzles-game-1\"},{\"categoryName\":\"Rubics Cubes\",\"siteUrl\":\"Testing-Team\"}]},{\"CatagoryName\":\"Dolls\",\"SiteUrl\":\"Dolls\",\"child\":[{\"categoryName\":\"Barbie\",\"siteUrl\":\"Barbie\"}]}]"
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
//        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { navigationItemSelected(it) }
        menuOptions = getMenuOption()
        val menu = navView.menu
        menuOptions?.forEachIndexed { index, options ->
            menu.add(R.id.menu_group_id, 200 + index, index, options.catagoryName)
            if (options.child != null && options.child.isNotEmpty()) {
                navView.menu.getItem(index).setActionView(R.layout.menu_image);
            }
        }
    }
    private fun getMenuOption(): Array<Options>? {
        val playerArray = Gson().fromJson(response, Array<Options>::class.java)
        return playerArray;
    }
    private fun navigationItemSelected(menuItem: MenuItem): Boolean {
        Toast.makeText(this, menuItem.title, Toast.LENGTH_SHORT).show()
        if (menuItem.actionView != null) {
            if (menuItem.actionView.background == null) {
                menuItem.actionView.setBackgroundColor(Color.BLUE)
                val navView: NavigationView = findViewById(R.id.nav_view)
                navView.menu.removeGroup(R.id.menu_group_id)
                if (menuOptions?.get(menuItem.order)?.child != null) {
                    menuOptions?.get(menuItem.order)?.child?.forEachIndexed { index, child ->
                        navView.menu.add(R.id.sub_menu_id, 300 + index, index, child.categoryName)
                    }
                }
            } else {
                menuItem.actionView.background = null
            }
        }
        return true;
    }
    override fun onBackPressed() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        val mDrawerLayout: DrawerLayout = findViewById(R.id.drawer_layout);
        if (navView.menu.get(0).groupId == R.id.sub_menu_id) {
            navView.menu.removeGroup(R.id.sub_menu_id)
            menuOptions?.forEachIndexed { index, options ->
                navView.menu.add(R.id.menu_group_id, 200 + index, index, options.catagoryName)
                if (options.child != null && options.child.isNotEmpty()) {
                    navView.menu.getItem(index).setActionView(R.layout.menu_image);
                }
            }
        } else if (navView.isVisible) {
            mDrawerLayout.closeDrawers()
        } else
            super.onBackPressed()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
