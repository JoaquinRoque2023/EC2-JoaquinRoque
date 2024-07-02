package pe.edu.idat.ec2_joaquinroque

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.idat.ec2_joaquinroque.ui.theme.EC2JoaquinRoqueTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EC2JoaquinRoqueTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "main_menu") {
                    composable("main_menu") { MainMenu(navController) }
                    composable("questionnaire") { Questionnaire(navController) }
                    composable("event_list") { EventList(navController) }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainMenu(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("AppDAT") })
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate("questionnaire") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("FORMULARIO", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("event_list") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("LISTADO", fontSize = 18.sp)
                }
            }
        }
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Questionnaire(navController: NavController) {

    val skills = listOf("Autoconocimiento", "Empatía", "Comunicación asertiva", "Toma de decisiones", "Pensamiento crítico", "Ninguno")
    val selectedSkills = remember { mutableStateListOf<String>() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cuestionario") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Text("1. Marque sus habilidades.", fontSize = 18.sp)
                    CheckboxList(skills, selectedSkills)
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    Text("2. ¿Cuán significativo es tu trabajo?", fontSize = 18.sp)
                    RadioButtonGroup(listOf("Mucho", "Más o menos", "Poco"))
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    Text("3. ¿Qué tan bien te pagan en el trabajo que haces?", fontSize = 18.sp)
                    RadioButtonGroup(listOf("Bien", "Regular", "Mal"))
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    Text("4. ¿Trabajas bajo presión?", fontSize = 18.sp)
                    YesNoRadioButtonGroup()
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    Text("5. ¿Tienes oportunidad de crecimiento en tu trabajo?", fontSize = 18.sp)
                    YesNoRadioButtonGroup()
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Resolver")
                    }
                }
            }
        }
    )
}

@Composable
fun CheckboxList(options: List<String>, selectedOptions: MutableList<String>) {
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                val isChecked = selectedOptions.contains(option)
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { checked ->
                        if (checked) {
                            selectedOptions.add(option)
                        } else {
                            selectedOptions.remove(option)
                        }
                    }
                )
                Text(text = option, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun RadioButtonGroup(options: List<String>) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) }
                )
                Text(text = option, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun YesNoRadioButtonGroup() {
    val options = listOf("Sí", "No")
    RadioButtonGroup(options)
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EventList(navController: NavController) {
    val events = List(12) { index ->
        Event(
            title = "Evento $index",
            description = "Descripción del evento $index",
            date = "Fecha del evento $index"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listado de Eventos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                items(events) { event ->
                    EventCard(event)
                }
            }
        }
    )
}

data class Event(val title: String, val description: String, val date: String)

@Composable
fun EventCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = event.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = event.description)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = event.date, fontStyle = FontStyle.Italic)
        }
    }
}

