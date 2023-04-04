package pe.edu.upc.todo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.todo.ui.theme.ToDoTheme

@Composable
fun MainScreen() {
    val tasks = remember {
        mutableStateListOf<String>()
    }

    val newTask = remember {
        mutableStateOf(TextFieldValue())
    }

    val isEditing = remember {
        mutableStateOf(false)
    }

    var ind = 0
    Scaffold(topBar = {

        TopAppBar(
            elevation = 4.dp,
            title = {
                Text(text = "Todo App")
            },
            actions = {
                IconButton(onClick = {
                    if(isEditing.value) {
                        tasks.set(ind, newTask.value.text)
                        isEditing.value = false
                        newTask.value = TextFieldValue()
                    }
                    else {

                        tasks.add(newTask.value.text)
                        newTask.value = TextFieldValue()
                    }

                }) {
                    if(isEditing.value) {
                        Icon(Icons.Filled.Done, null)
                    } else {
                        Icon(Icons.Filled.Add, null)
                    }

                }
            }
        )
    }) { it ->
        Column(modifier = Modifier.padding(it)) {
            TextField(
                label = { Text(text = "New task") },
                value = newTask.value,
                onValueChange = {
                    newTask.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { newTask.value = TextFieldValue() }) {
                        if (isEditing.value) {
                            Icon(Icons.Filled.Delete, null)
                        }
                        else {
                            Icon(Icons.Default.Person, null)
                        }
                    }

                }
            )
            Tasks(tasks) { value ->
                newTask.value = TextFieldValue(value)
                isEditing.value = true
                ind = tasks.indexOf(newTask.value.text)
            }
        }

    }
}


@Composable
fun Tasks(tasks: MutableList<String>, selectTask: (String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tasks) { task ->
            TaskItem(
                task = task,
                deleteTask = {
                    tasks.remove(it)
                },
                selectTask = {
                    selectTask(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskItem(
    task: String,
    deleteTask: (String) -> Unit,
    selectTask: (String) -> Unit
) {
    Card(onClick = {
        selectTask(task)
    }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = task)
            IconButton(
                onClick = {
                    deleteTask(task)
                }) {
                Icon(Icons.Filled.Delete, null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoTheme {
        MainScreen()
    }
}