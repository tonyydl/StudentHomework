package tony.studenthomework.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import tony.studenthomework.R
import tony.studenthomework.model.RecordStatus
import tony.studenthomework.model.RecordStatusEnum
import tony.studenthomework.model.RecordedHomework
import tony.studenthomework.model.StudentDetail

enum class MainScreen {
    List,
    Record
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainAppBar(
    appbarTitle: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(appbarTitle) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = actions
    )
}

@Composable
fun MainApp(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MainScreen.valueOf(
        backStackEntry?.destination?.route ?: MainScreen.List.name
    )
    val appbarTitle = if (currentScreen == MainScreen.Record) {
        "[%s] %s".format(uiState.selectedStudentDetail?.id, uiState.selectedStudentDetail?.name)
    } else {
        stringResource(id = R.string.app_name)
    }

    Scaffold(
        topBar = {
            MainAppBar(
                appbarTitle = appbarTitle,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = if (currentScreen == MainScreen.Record &&
                    !uiState.selectedStudentDetail?.recordedHomework.isNullOrEmpty()
                ) {
                    {
                        FloatingActionButton(
                            onClick = {
                                viewModel.updateRecords()
                                navController.popBackStack()
                            }
                        ) {
                            Icon(Icons.Filled.Done, null)
                        }
                    }
                } else {
                    {}
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainScreen.List.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MainScreen.List.name) {
                LaunchedEffect(Unit) {
                    viewModel.fetchStudentList()
                }
                StudentListScreen(
                    studentList = uiState.studentList,
                    onClick = { studentId ->
                        viewModel.setSelectedStudentId(studentId)
                        navController.navigate(MainScreen.Record.name)
                    }
                )
            }
            composable(route = MainScreen.Record.name) {
                LaunchedEffect(Unit) {
                    viewModel.fetchStudentDetailById(uiState.selectedStudentId)
                }
                val studentDetail = uiState.selectedStudentDetail
                StudentRecordScreen(
                    studentDetail = studentDetail,
                    onClickItem = { index, checked ->
                        val recordedHomework = studentDetail?.recordedHomework?.get(index)
                        val recordStatus = if (checked) {
                            RecordStatus(RecordStatusEnum.DONE.ordinal, RecordStatusEnum.DONE.name)
                        } else {
                            RecordStatus(
                                RecordStatusEnum.PROCESSING.ordinal,
                                RecordStatusEnum.PROCESSING.name
                            )
                        }
                        val newRecordedHomework =
                            RecordedHomework(recordedHomework?.homework, recordStatus)
                        val newRecordedHomeworkList =
                            studentDetail?.recordedHomework?.toMutableList()
                        newRecordedHomeworkList?.set(index, newRecordedHomework)
                        val newStudentDetail = StudentDetail(
                            studentDetail?.id ?: 0,
                            studentDetail?.number.orEmpty(),
                            studentDetail?.name.orEmpty(),
                            newRecordedHomeworkList
                        )
                        viewModel.setSelectedStudentDetail(newStudentDetail)
                    }
                )
            }
        }
    }
}