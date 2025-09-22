export interface WithId {
  id: string;
}

export interface NewActivityDTO {
  name: string;
  description: string;
}

export interface ActivityDTO extends NewActivityDTO, WithId {}

export interface NewTeamDTO {
  name: string;
}

export interface TeamDTO extends NewTeamDTO, WithId {}
