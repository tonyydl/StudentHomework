package tony.studenthomework.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import tony.studenthomework.data.dto.Student

@Composable
fun StudentListScreen(
    studentList: List<Student>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (studentList.isNotEmpty()) {
        LazyColumn(modifier = modifier) {
            items(studentList) { student ->
                StudentRow(
                    name = student.name.orEmpty(),
                    number = student.number.orEmpty(),
                    onClick = {
                        onClick(student.id)
                    }
                )
            }
        }
    } else {
        EmptyView()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStudentListScreen() {
    val studentList = listOf(
        Student(1, "A0001", "Tony"),
        Student(1, "A0002", "Amy"),
        Student(1, "A0003", "James"),
        Student(1, "A0004", "Tenz")
    )
    StudentListScreen(
        studentList = studentList,
        onClick = {}
    )
}

@Composable
private fun StudentRow(
    name: String,
    number: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Black,
                fontSize = 24.sp
            )
        )
        Text(
            text = number,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 16.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStudentInfo() {
    StudentRow(name = "A0001", number = "Tony", onClick = {})
}