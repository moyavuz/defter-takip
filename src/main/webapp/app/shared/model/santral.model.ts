export interface ISantral {
    id?: number;
    ad?: string;
}

export class Santral implements ISantral {
    constructor(public id?: number, public ad?: string) {}
}
