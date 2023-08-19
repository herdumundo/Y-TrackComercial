package com.ytrack.y_trackcomercial.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDownCircle
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ytrack.y_trackcomercial.ui.theme.Gray200
import com.ytrack.y_trackcomercial.ui.theme.Gray600
import com.ytrack.y_trackcomercial.ui.theme.Green500
import com.ytrack.y_trackcomercial.ui.theme.LightBlue900
import com.ytrack.y_trackcomercial.ui.theme.MedGray3
import com.ytrack.y_trackcomercial.ui.theme.accordionHeaderStyle
import com.ytrack.y_trackcomercial.ui.theme.bodyBold
import com.ytrack.y_trackcomercial.ui.theme.tags

data class AccordionModel(
    val header: String,
    val rows: List<Row>
) {
data class Row(
        val title: String,
        val valor: String
    )
}

/*@Composable
fun AccordionGroup(modifier: Modifier = Modifier, group: List<AccordionModel>) {

 Column(modifier = modifier) {
      group.forEach {
          Accordion(model = it)
      }
  }
}*//*
@Composable
fun AccordionGroup(modifier: Modifier = Modifier, group: List<AccordionModel>) {
        Column {
            group.forEach {
                Accordion(model = it)
            }

    }
}*/

@Composable
fun AccordionGroup(modifier: Modifier = Modifier, group: List<AccordionModel>) {
    Box(
      //  modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            group.forEach {
                Accordion(model = it)
            }
        }
    }
}


@Composable
fun Accordion(modifier: Modifier = Modifier, model: AccordionModel) {
  var expanded by remember { mutableStateOf(false) }
  Column(modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {

      AccordionHeader(title = model.header, isExpanded = expanded) {
          expanded = !expanded
      }

       AnimatedVisibility(visible = expanded) {
          Surface(
              color = White,
              shape = RoundedCornerShape(8.dp),
              border = BorderStroke(1.dp, Gray200),
              elevation = 5.dp,
              modifier = Modifier.padding(top = 8.dp)
          ) {
               Column {
                   model.rows.forEach {
                       AccordionRow(it)
                       Divider(color = Gray200, thickness = 1.dp)
                   }
                   /*items(model.rows) { row ->
                      AccordionRow(row)
                      Divider(color = Gray200, thickness = 1.dp)
                  } */
              }
          }
      }
  }
}
@Preview
@Composable
private fun AccordionHeader(
  title: String = "Header",
  isExpanded: Boolean = false,
  onTapped: () -> Unit = {}
) {
  val degrees = if (isExpanded) 180f else 0f

  Surface(
      color = White,
      shape = RoundedCornerShape(8.dp),
      border = BorderStroke(1.dp, Gray200),
      elevation = 8.dp,
  ) {
      Row(
          modifier = Modifier
              .clickable { onTapped() }
              .padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
      ) {
          Text(title, Modifier.weight(1f), style = accordionHeaderStyle, color = Gray600)
          Surface(shape = CircleShape, color = LightBlue900.copy(alpha = 0.6f)) {
              Icon(
                  Icons.Outlined.ArrowDropDown,
                  contentDescription = "arrow-down",
                  modifier = Modifier.rotate(degrees),
                  tint = White
              )
          }
      }
  }
}

@Preview
@Composable
private fun AccordionRow(
  model: AccordionModel.Row = AccordionModel.Row("AAPL", "$328.89")
) {
  Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(8.dp)
  ) {
      Text(model.title, Modifier.weight(1f), style = tags, color = MedGray3)
      Surface(color = Green500, shape = RoundedCornerShape(8.dp), elevation = 2.dp) {
          Text(
              text = model.valor,
              modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
              style = bodyBold,
              color = White
          )
      }
  }
}