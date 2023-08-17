package tony.studenthomework.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tony.studenthomework.R
import tony.studenthomework.model.Homework
import tony.studenthomework.model.RecordStatus
import tony.studenthomework.model.RecordStatusEnum
import tony.studenthomework.model.RecordedHomework
import tony.studenthomework.model.StudentDetail

@Composable
fun StudentRecordScreen(
    studentDetail: StudentDetail?,
    onClickItem: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (studentDetail != null && !studentDetail.recordedHomework.isNullOrEmpty()) {
        LazyColumn(modifier = modifier) {
            itemsIndexed(studentDetail.recordedHomework) { index, recordHomework ->
                RecordRow(
                    title = recordHomework.homework.title,
                    status = recordHomework.status.id,
                    onClickItem = { checked ->
                        onClickItem(index, checked)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } else {
        EmptyView()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStudentRecordScreen() {
    val recordedHomework = mutableListOf<RecordedHomework>()
    recordedHomework.add(RecordedHomework(Homework(1, "Two Sum"), RecordStatus(1, "PROCESSING")))
    recordedHomework.add(RecordedHomework(Homework(2, "Add Two Numbers"), RecordStatus(2, "DONE")))
    val studentDetail = StudentDetail(
        1,
        "A0001",
        "Tony Yang",
        recordedHomework
    )
    StudentRecordScreen(
        studentDetail = studentDetail,
        onClickItem = { _, _ -> },
    )
}

@Composable
private fun RecordRow(
    title: String,
    status: Int,
    onClickItem: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var checked by remember { mutableStateOf(status == RecordStatusEnum.DONE.ordinal) }
    Row(
        modifier = modifier
            .padding(16.dp)
            .clickable(onClick = {
                checked = !checked
                onClickItem(checked)
            }),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = title,
            fontSize = 20.sp,
            maxLines = 2
        )
        Spacer(modifier = Modifier.weight(1f))
        Checkbox(
            checked = checked,
            onCheckedChange = { newChecked ->
                checked = newChecked
                onClickItem(checked)
            },
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRecordRow() {
    RecordRow(
        title = "Title",
        status = 2,
        onClickItem = {}
    )
}