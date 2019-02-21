export interface IMarka {
    id?: number;
    ad?: string;
}

export class Marka implements IMarka {
    constructor(public id?: number, public ad?: string) {}
}
