import * as React from 'react'
import { ViewProperties } from "react-native";

declare module "react-native-sketch-draw" {
  export interface SketchDrawProps {
    selectedTool: number
    toolColor: string
    localSourceImagePath: string
    toolThickness: number
    onSaveSketch(imageResult: ImageResult): void
  }

  export interface ImageResult {
    localFilePath: string
    imageWidth: number
    imageHeight: number
  }

  export interface Constants {
    toolType: Tool
  }

  export interface Tool {
    pen: ToolType
    eraser: ToolType
  }

  export interface ToolType {
    id: number
		name: string
  }

  export default class SketchDraw extends React.Component<SketchDrawProps & ViewProperties> {
    static constants: Constants
  }

  // export function onSaveSketch(imageResult: ImageResult): void
}
