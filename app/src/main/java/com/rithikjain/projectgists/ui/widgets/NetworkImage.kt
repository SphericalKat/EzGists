package com.rithikjain.projectgists.ui.widgets

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun NetworkImage(
  url: String,
  modifier: Modifier = Modifier.preferredSizeIn(maxHeight = 64.dp).padding(8.dp)
) {
  var image by remember { mutableStateOf<ImageAsset?>(null) }
  var drawable by remember { mutableStateOf<Drawable?>(null) }
  val context = ContextAmbient.current

  onCommit(url) {
    val glide = Glide.with(context)
    val target = object : CustomTarget<Bitmap>() {
      override fun onLoadCleared(placeholder: Drawable?) {
        image = null
        drawable = placeholder
      }

      override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
        image = bitmap.asImageAsset()
      }
    }

    glide.asBitmap().load(url).into(target)

    onDispose {
      image = null
      drawable = null
      glide.clear(target)
    }
  }

  val theImage = image
  val theDrawable = drawable
  if (theImage != null) {
    Box(
      modifier.clip(RoundedCornerShape(50)),
      alignment = Alignment.Center,
    ) {
      Image(asset = theImage)
    }

  } else if (theDrawable != null) {
    Canvas(modifier = modifier) {
      drawIntoCanvas { canvas ->
        theDrawable.draw(canvas.nativeCanvas)
      }
    }
  }
}