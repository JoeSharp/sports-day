export interface NewActivityDTO {
  name: string;
  description: string;
}
export interface ActivityDTO extends NewActivityDTO {
  id: string;
}
