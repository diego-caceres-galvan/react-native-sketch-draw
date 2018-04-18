import { Component } from 'react';
import { ViewProperties } from "react-native";

declare module "react-native-sketch-draw" {
  export interface SketchDrawProps {
    selectedTool: number
    toolColor: string
    localSourceImagePath: string
    toolThickness: number
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
    id: string
		name: string
  }

  export default class SketchDraw extends Component<SketchDrawProps & ViewProperties> {
    constants: Constants;

    onSaveSketch(imageResult: ImageResult): void;
  }

  export function onSaveSketch(imageResult: ImageResult): void
}
