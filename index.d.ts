import { ViewProperties } from "react-native";

declare module "react-native-sketch-draw" {
  // export interface SketchDrawProps {
  //   selectedTool: number
  //   toolColor: string
  //   localSourceImagePath: string
  //   toolThickness: number
  // }
  export default class SketchDraw extends Component<SketchDrawProps & ViewProperties> {
    static Constants: Constants;

    onSaveSketch(imageResult: ImageResult): void;
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

  export function onSaveSketch(imageResult: ImageResult): void

  export default SketchDraw;
}
